package filters;

import com.google.common.collect.Sets;
import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.model.ApiDescription;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.Property;
import play.Play;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SwaggerProxyFilter implements SwaggerSpecFilter {

    private static final Set defaultHeaders = Sets.newHashSet("Edt-Server-Domain",
            "Edt-Device-Type",
            "Edt-Os-Type",
            "Edt-Lang-Cd",
            "Edt-App-Id",
            "Edt-App-Ver",
            "Cache-Control",
            "Accept");

    NexshopAuthenticator authenticator;

    @Override
    public boolean isOperationAllowed(Operation operation, ApiDescription api, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        authenticator = Play.application().injector().instanceOf(NexshopAuthenticator.class);
        setDefaultEdtHeaders(operation);
        replaceOrCreateHeader(operation, "Edt-Cookie", authenticator.getEduttoCookie());
        return true;
    }

    @Override
    public boolean isParamAllowed(Parameter parameter, Operation operation, ApiDescription api, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        return true;
    }

    @Override
    public boolean isPropertyAllowed(Model model, Property property, String propertyName, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        return true;
    }

    private void setDefaultEdtHeaders(Operation operation) {
        if (!hasEdtHeaderParameters(operation)) {
            HeaderParameter edtServerDomain = createHeaderParameter("Edt-Server-Domain", "https://v2-stage.edutto.net/frt");
            HeaderParameter edtDeviceType = createHeaderParameter("Edt-Device-Type", "Phone");
            HeaderParameter edtOsType = createHeaderParameter("Edt-Os-Type", "Android");
            HeaderParameter edtLangCd = createHeaderParameter("Edt-Lang-Cd", "ko_KR");
            HeaderParameter edtAppId = createHeaderParameter("Edt-App-Id", "com.scic3.ciw.wrkm");
            HeaderParameter edtAppVer = createHeaderParameter("Edt-App-Ver", "2.0.38");
            HeaderParameter cacheControl = createHeaderParameter("Cache-Control", "no-cache");
            HeaderParameter accept = createHeaderParameter("Accept", "application/json");

            operation.parameter(edtServerDomain);
            operation.parameter(edtDeviceType);
            operation.parameter(edtOsType);
            operation.parameter(edtLangCd);
            operation.parameter(edtAppId);
            operation.parameter(edtAppVer);
            operation.parameter(cacheControl);
            operation.parameter(accept);
        }
    }

    private void replaceOrCreateHeader(Operation operation, String headerName, String defaultValue) {
        boolean hasHeader = false;

        List<Parameter> parameters = operation.getParameters();

        for (Parameter param : parameters) {
            if (param instanceof HeaderParameter && param.getName().equals(headerName)) {
                hasHeader = true;
                ((HeaderParameter) param).setDefaultValue(defaultValue);
            }
        }
        if (!hasHeader) {
            HeaderParameter headerParameter = createHeaderParameter(headerName, defaultValue);
            operation.parameter(headerParameter);
        }
    }

    private HeaderParameter createHeaderParameter(String name, String defaultValue) {
        HeaderParameter headerParameter = new HeaderParameter();
        headerParameter.setType("string");
        headerParameter.setName(name);
        headerParameter.setDefaultValue(defaultValue);
        return headerParameter;
    }

    private boolean hasEdtHeaderParameters(Operation operation) {
        List<Parameter> parameters = operation.getParameters();
        boolean hasEdtHeaderParameter = false;

        for (Parameter param : parameters) {
            if (defaultHeaders.contains(param.getName())) {
                hasEdtHeaderParameter = true;
                break;
            }
        }
        return hasEdtHeaderParameter;
    }
}
