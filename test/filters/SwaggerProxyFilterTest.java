package filters;

import com.google.inject.Guice;
import io.swagger.models.Operation;
import io.swagger.models.parameters.HeaderParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import play.Application;
import play.ApplicationLoader;
import play.Environment;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.GuiceApplicationLoader;
import play.test.WithApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static play.inject.Bindings.bind;

@RunWith(MockitoJUnitRunner.class)
public class SwaggerProxyFilterTest extends WithApplication {

    @Mock
    NexshopAuthenticator authenticator;

    @InjectMocks
    SwaggerProxyFilter swaggerProxyFilter;

    Operation operation = new Operation();

    @Override
    protected Application provideApplication() {

        GuiceApplicationBuilder builder = new GuiceApplicationLoader()
                .builder(new ApplicationLoader.Context(Environment.simple()))
                .overrides(bind(NexshopAuthenticator.class).toInstance(authenticator));

        Guice.createInjector(builder.applicationModule()).injectMembers(this);

        return builder.build();
    }

    @Test
    public void afterOperationFilter_addHeader_whenNoHeaders() throws Exception {
        boolean operationAllowed = swaggerProxyFilter.isOperationAllowed(operation, null, null, null, null);
        assertThat(operationAllowed).isTrue();

        assertThat(operation.getParameters().size()).isEqualTo(9);
        assertThat(operation.getParameters().get(0).getName()).isEqualTo("Edt-Server-Domain");
        assertThat(operation.getParameters().get(1).getName()).isEqualTo("Edt-Device-Type");
        assertThat(operation.getParameters().get(2).getName()).isEqualTo("Edt-Os-Type");
        assertThat(operation.getParameters().get(3).getName()).isEqualTo("Edt-Lang-Cd");
        assertThat(operation.getParameters().get(4).getName()).isEqualTo("Edt-App-Id");
        assertThat(operation.getParameters().get(5).getName()).isEqualTo("Edt-App-Ver");
        assertThat(operation.getParameters().get(6).getName()).isEqualTo("Cache-Control");
        assertThat(operation.getParameters().get(7).getName()).isEqualTo("Accept");
        assertThat(operation.getParameters().get(8).getName()).isEqualTo("Edt-Cookie");
    }

    @Test
    public void afterOperationFilter_noChangesDefaultHeader_whenSomeHeaderIsAlreadyExisted() throws Exception {
        operation.parameter(createHeaderParameter("Edt-Server-Domain", "https://v2-stage.edutto.net/frt"));

        boolean operationAllowed = swaggerProxyFilter.isOperationAllowed(operation, null, null, null, null);
        assertThat(operationAllowed).isTrue();

        assertThat(operation.getParameters().size()).isEqualTo(2);
        assertThat(operation.getParameters().get(0).getName()).isEqualTo("Edt-Server-Domain");
        assertThat(((HeaderParameter) operation.getParameters().get(0)).getDefaultValue()).isEqualTo("https://v2-stage.edutto.net/frt");

        assertThat(operation.getParameters().get(1).getName()).isEqualTo("Edt-Cookie");
    }

    @Test
    public void afterOperationFilter_replaceDynamicHeader() throws Exception {
        when(authenticator.getEduttoCookie()).thenReturn("new Cookie");
        operation.parameter(createHeaderParameter("Edt-Server-Domain", "https://v2-stage.edutto.net/frt"));
        operation.parameter(createHeaderParameter("Edt-Cookie", "old cookie"));

        boolean operationAllowed = swaggerProxyFilter.isOperationAllowed(operation, null, null, null, null);
        assertThat(operationAllowed).isTrue();

        assertThat(operation.getParameters().size()).isEqualTo(2);
        assertThat(operation.getParameters().get(0).getName()).isEqualTo("Edt-Server-Domain");

        assertThat(operation.getParameters().get(1).getName()).isEqualTo("Edt-Cookie");
        assertThat(((HeaderParameter) operation.getParameters().get(1)).getDefaultValue()).isEqualTo("new Cookie");
    }

    private HeaderParameter createHeaderParameter(String name, String defaultValue) {
        HeaderParameter edtServerDomain = new HeaderParameter();
        edtServerDomain.setType("string");
        edtServerDomain.setName(name);
        edtServerDomain.setDefaultValue(defaultValue);
        return edtServerDomain;
    }
}