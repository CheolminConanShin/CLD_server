package apis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Http;
import play.mvc.Http.Request;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LegacyConnectorTest {

    private static final String COOKIE_HEADER = "any cookie";
    private static final String TOKEN_HEADER = "any token";
    private static final String DEVICE_TYPE_HEADER = "any device";
    private static final String OS_TYPE_HEADER = "any os type";
    private static final String LANG_CODE_HEADER = "any language code";
    private static final String APPLICATION_ID_HEADER = "any application id";
    private static final String APPLICATION_VERSION_HEADER = "any application version";
    private static final String CACHE_CONTROL_HEADER = "any cache control";
    private static final String ACCEPT_HEADER = "any accept";
    private static final String CONTENT_TYPE_HEADER = "any content type";
    private static final String SERVER_DOMAIN = "any server domain";

    @Mock
    private WSClient wsClient;
    @Mock
    private Request incomingRequest;
    @Mock
    WSRequest outgoingRequest;
    @InjectMocks
    private LegacyConnector legacyConnector;

    @Before
    public void setUp() throws Exception {
        Http.Context context = mock(Http.Context.class);
        when(context.request()).thenReturn(incomingRequest);

        Http.Context.current.set(context);
    }

    @Test
    public void shouldInjectRequestHeaders() {
        addHeaders(incomingRequest);

        String requestPath = "/request.uri";
        when(wsClient.url(SERVER_DOMAIN + requestPath)).thenReturn(outgoingRequest);

        legacyConnector.makeRequest(requestPath);

        verifyHeadersApplied(outgoingRequest);


    }

    private void addHeaders(Request incomingRequest) {
        when(incomingRequest.getHeader("Edt-Server-Domain")).thenReturn(SERVER_DOMAIN);
        when(incomingRequest.getHeader("Cookie")).thenReturn(COOKIE_HEADER);
        when(incomingRequest.getHeader("Edt-Id-Token")).thenReturn(TOKEN_HEADER);
        when(incomingRequest.getHeader("Edt-Device-Type")).thenReturn(DEVICE_TYPE_HEADER);
        when(incomingRequest.getHeader("Edt-Os-Type")).thenReturn(OS_TYPE_HEADER);
        when(incomingRequest.getHeader("Edt-Lang-Cd")).thenReturn(LANG_CODE_HEADER);
        when(incomingRequest.getHeader("Edt-App-Id")).thenReturn(APPLICATION_ID_HEADER);
        when(incomingRequest.getHeader("Edt-App-Ver")).thenReturn(APPLICATION_VERSION_HEADER);
        when(incomingRequest.getHeader("Cache-Control")).thenReturn(CACHE_CONTROL_HEADER);
        when(incomingRequest.getHeader("Accept")).thenReturn(ACCEPT_HEADER);
        when(incomingRequest.getHeader("Content-Type")).thenReturn(CONTENT_TYPE_HEADER);
    }

    private void verifyHeadersApplied(WSRequest outgoingRequest) {
        verify(outgoingRequest).setHeader("Cookie", COOKIE_HEADER);
        verify(outgoingRequest).setHeader("Edt-Id-Token", TOKEN_HEADER);
        verify(outgoingRequest).setHeader("Edt-Device-Type", DEVICE_TYPE_HEADER);
        verify(outgoingRequest).setHeader("Edt-Os-Type", OS_TYPE_HEADER);
        verify(outgoingRequest).setHeader("Edt-Lang-Cd", LANG_CODE_HEADER);
        verify(outgoingRequest).setHeader("Edt-App-Id", APPLICATION_ID_HEADER);
        verify(outgoingRequest).setHeader("Edt-App-Ver", APPLICATION_VERSION_HEADER);
        verify(outgoingRequest).setHeader("Cache-Control", CACHE_CONTROL_HEADER);
        verify(outgoingRequest).setHeader("Accept", ACCEPT_HEADER);
        verify(outgoingRequest).setContentType(CONTENT_TYPE_HEADER);
    }
}