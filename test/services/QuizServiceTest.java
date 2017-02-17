package services;

import apis.QuizLegacyConnector;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.Mockito.verify;

public class QuizServiceTest {

    private QuizService subject;

    @Mock
    private QuizLegacyConnector mockQuizLegacyConnector;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        subject = new QuizService(mockQuizLegacyConnector);
    }

    @Test
    public void getQuizzes_apiCalls() throws Exception {
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", 12);
        subject.getQuizzes(params);

        verify(mockQuizLegacyConnector).selectQuizList(params);
    }
}