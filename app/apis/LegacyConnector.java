package apis;

import com.google.inject.Inject;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Http;

public class LegacyConnector {

    @Inject
    private WSClient ws;


    public WSRequest makeRequest(String url){
        String baseUrl = "http://localhost:8080/frt";
        WSRequest request = ws.url(baseUrl + url);

        Http.Request httpRequest = Http.Context.current().request();

        request.setHeader("Cookie", httpRequest.getHeader("Cookie"));
        request.setHeader("Edt-Id-Token", httpRequest.getHeader("Edt-Id-Token"));
        request.setHeader("Edt-Device-Type", httpRequest.getHeader("Edt-Device-Type"));
        request.setHeader("Edt-Os-Type", httpRequest.getHeader("Edt-Os-Type"));
        request.setHeader("Edt-Lang-Cd", httpRequest.getHeader("Edt-Lang-Cd"));
        request.setHeader("Edt-App-Id", httpRequest.getHeader("Edt-App-Id"));
        request.setHeader("Edt-App-Ver", httpRequest.getHeader("Edt-App-Ver"));
        request.setHeader("Cache-Control", httpRequest.getHeader("Cache-Control"));
        request.setHeader("Accept", httpRequest.getHeader("Accept"));
        request.setContentType(httpRequest.getHeader("Content-Type"));

        return request;
    }
}
