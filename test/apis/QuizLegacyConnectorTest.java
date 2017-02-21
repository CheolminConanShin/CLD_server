package apis;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import models.legacy.LegacyQuiz;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import play.libs.F;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Http;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuizLegacyConnectorTest {

    @Mock
    private WSClient mockClient;
    @InjectMocks
    private QuizLegacyConnector subject;

    @Before
    public void setUp() throws Exception {
        Http.Context context = mock(Http.Context.class);
        when(context.request()).thenReturn(mock(Http.Request.class));

        Http.Context.current.set(context);
    }

    @Test
    public void callApi_returnLists() throws Exception {
        WSRequest mockRequest = mock(WSRequest.class);
        F.Promise responsePromise = mock(F.Promise.class);
        F.Promise mockPromise = mock(F.Promise.class);

        when(mockClient.url(anyString())).thenReturn(mockRequest);
        when(mockRequest.post(any(JsonNode.class))).thenReturn(responsePromise);
        when(responsePromise.map(any())).thenReturn(mockPromise);
        when(mockPromise.get(anyLong())).thenReturn(Arrays.asList(mock(LegacyQuiz.class)));

        List<LegacyQuiz> legacyQuizzes = subject.selectQuizList(Maps.newHashMap());

        assertThat(legacyQuizzes.size()).isEqualTo(1);
    }
}