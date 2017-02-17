package apis;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import models.legacy.LegacyQuiz;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

public class QuizLegacyConnectorTest {
    private QuizLegacyConnector subject;

    @Mock @Bind
    private WSClient mockClient;

    @Before
    public void setUp() throws Exception {
        subject = new QuizLegacyConnector();

        MockitoAnnotations.initMocks(this);
        Guice.createInjector(BoundFieldModule.of(this)).injectMembers(subject);

        Http.Context context = mock(Http.Context.class);
        Http.Request request = mock(Http.Request.class);
        Http.Context.current.set(context);
        when(context.request()).thenReturn(request);
    }

    @Test
    public void callApi_returnLists() throws Exception {
        WSRequest mockRequest = mock(WSRequest.class);
        F.Promise responsePromise = mock(F.Promise.class);
        F.Promise mockPromise = mock(F.Promise.class);

        when(mockClient.url(anyString())).thenReturn(mockRequest);
        when(mockRequest.post(any(JsonNode.class))).thenReturn(responsePromise);
        when(responsePromise.map(any())).thenReturn(mockPromise);
        when(mockPromise.get(anyLong())).thenReturn(Arrays.asList(new LegacyQuiz()));

        List<LegacyQuiz> legacyQuizzes = subject.selectQuizList(Maps.newHashMap());

        assertThat(legacyQuizzes.size()).isEqualTo(1);
    }
}