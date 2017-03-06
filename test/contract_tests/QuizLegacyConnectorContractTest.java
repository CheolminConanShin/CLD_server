package contract_tests;

import apis.QuizLegacyConnector;
import com.ning.http.client.AsyncHttpClientConfig;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.ws.WSClient;
import play.libs.ws.ning.NingWSClient;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.test.WithApplication;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.inject.Bindings.bind;

public class QuizLegacyConnectorContractTest extends WithApplication {

    private static final String TOKEN_HEADER = "eyJhbGciOiJIUzI1NiIsImtpZCI6ImsxIn0.eyJhdWQiOiJlZHV0dG9fbW9iIiwiaWF0IjoxNDg4Nzc2OTk1LCJzdWIiOiJ5b25nd29vbi5oYW4iLCJpc3MiOiJodHRwczovL2F1dGhzdGFnZS5lZHV0dG8ubmV0IiwiZXhwIjoxNTIwMzEyOTk1LCJqdGkiOiI3YkxWNVFaWDJ0ZEdheWNjaWwwYUFRIiwicmVkaXJlY3RfdXJpIjpudWxsLCJzZXNzaW9uX3N0YXRlIjoiU1M2NjQ1OGU3MzNlNjk0Njg1YmFkZTQyMjhjNmYxNzc2NCIsInRlbmFudF9pZCI6bnVsbH0.myesbnUD4aByJQkJrtDKuDPCYD_kE4rd0PO9lOd92-Y";
    private static final String DEVICE_TYPE_HEADER = "Phone";
    private static final String OS_TYPE_HEADER = "Android";
    private static final String LANG_CODE_HEADER = "ko-KR";
    private static final String APPLICATION_ID_HEADER = "com.scic3.ciw.wrkm";
    private static final String APPLICATION_VERSION_HEADER = "2.0.37";
    private static final String CONTENT_TYPE_HEADER = "application/json";
    private static final String SERVER_DOMAIN = "http://70.50.170.218:8080/frt";

    private QuizLegacyConnector quizLegacyConnector;
    private WSClient wsClient;

    @Override
    protected Application provideApplication() {
        wsClient = new NingWSClient(new AsyncHttpClientConfig.Builder().setFollowRedirect(true).build());

        return new GuiceApplicationBuilder()
                .overrides(bind(WSClient.class).toInstance(wsClient))
                .build();
    }


    @Before
    public void setupFakeIncomingRequest() {
        Http.Context context = mock(Http.Context.class);
        Request fakeIncomingRequest = mock(Request.class);
        addRequiredHeaders(fakeIncomingRequest);
        when(context.request()).thenReturn(fakeIncomingRequest);

        Http.Context.current.set(context);
        quizLegacyConnector = new QuizLegacyConnector();
        quizLegacyConnector.setWs(wsClient);
    }

    @Ignore@Test
    public void shouldDeserializeResponseIntoObjectGraphWithoutThrowingException() {

        String January16 = "2017-01-16";
        String February16 = "2017-02-16";

        quizLegacyConnector.selectQuizList(dateParams(January16, February16));
    }

    private Map<String, Object> dateParams(String today, String oneMonthFromToday) {
        Map<String, Object> params = new HashedMap();
        params.put("searchStartDate", today);
        params.put("searchEndDate", oneMonthFromToday);
        return params;
    }

    private void addRequiredHeaders(Request incomingRequest) {
        when(incomingRequest.getHeader("Edt-Server-Domain")).thenReturn(SERVER_DOMAIN);
        when(incomingRequest.getHeader("Edt-Id-Token")).thenReturn(TOKEN_HEADER);
        when(incomingRequest.getHeader("Edt-Device-Type")).thenReturn(DEVICE_TYPE_HEADER);
        when(incomingRequest.getHeader("Edt-Os-Type")).thenReturn(OS_TYPE_HEADER);
        when(incomingRequest.getHeader("Edt-Lang-Cd")).thenReturn(LANG_CODE_HEADER);
        when(incomingRequest.getHeader("Edt-App-Id")).thenReturn(APPLICATION_ID_HEADER);
        when(incomingRequest.getHeader("Edt-App-Ver")).thenReturn(APPLICATION_VERSION_HEADER);
        when(incomingRequest.getHeader("Content-Type")).thenReturn(CONTENT_TYPE_HEADER);
    }
}