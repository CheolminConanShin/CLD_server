package services;

import apis.QuizLegacyConnector;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.transformers.QuizTransformer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuizServiceTest {

    @Mock
    private QuizLegacyConnector mockQuizLegacyConnector;

    @Mock
    private QuizTransformer quizTransformer;

    @InjectMocks
    private QuizService subject;

    @Test
    public void getQuizzes_apiCalls() throws Exception {
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", 12);
        subject.getQuizzes(params);

        verify(mockQuizLegacyConnector).selectQuizList(params);
    }

    @Test
    public void shouldTransformApiResponseObject() throws ParseException {
        Map params = mock(Map.class);
        List legacyQuizzes = new ArrayList();
        when(mockQuizLegacyConnector.selectQuizList(params)).thenReturn(legacyQuizzes);

        subject.getQuizzes(params);

        verify(quizTransformer).transform(legacyQuizzes);
    }
}