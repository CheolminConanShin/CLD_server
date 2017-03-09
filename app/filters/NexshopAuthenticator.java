package filters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Joiner;
import com.google.inject.Inject;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;

public class NexshopAuthenticator {
    private static final String ID_PASSWORD_SHA512 = "Basic eW9uZ3dvb24uaGFuOnE3ZllIZ25ON3hmUXYxemNnbkhCblhhL0hZYWpvbHVzaUdTN0l4aXNmei9qS1pXTmZDMWpDRlUzZ1hxY1BiZE83bFYzVzNqd3IvaFFYQ283cSt5MlFBPT0=";
    private static final String AUTH_URL = "https://authstage.edutto.net/oauth/authorize?grant_type=authorization_code&scope=openid&response_type=code&client_id=edutto_mob";
    private static final String SERVER_URL = "https://v2-stage.edutto.net/frt/cmm/eco/usr/LoginMob/selectMobileLogin.mob";

    @Inject
    WSClient ws;

    public String getEduttoCookie(){
        String authorizationCode = getAuthorizationCode();

        WSRequest request = ws.url(SERVER_URL);
        ObjectNode parameter = Json.newObject();
        parameter.put("lowTypYn", "N");
        parameter.put("langCd", "en_US");
        parameter.put("accsSysTypCd", "PHN01");
        parameter.put("dvcNm", "Android SDK built for x86_64");
        parameter.put("baiduPushYn", "N");
        parameter.put("dvcId", "9bd67fc0368f2f74");
        parameter.put("osVerNm", "5.1.1");
        parameter.put("code", authorizationCode);

        F.Promise<String> promise = request.post(parameter).map(response -> {
            return Joiner.on("; ").join(response.getAllHeaders().get("Set-Cookie"));
        });

        return promise.get(30000);
    }

    private String getAuthorizationCode() {
        WSRequest request = ws.url(AUTH_URL);
        request.setHeader("Authorization", ID_PASSWORD_SHA512);

        F.Promise<String> promise = request.get().map(response -> {
            JsonNode body = response.asJson();
            return body.get("code").asText();
        });
        return promise.get(30000);
    }
}
