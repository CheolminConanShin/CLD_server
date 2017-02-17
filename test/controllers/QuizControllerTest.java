package controllers;

import com.google.common.collect.Maps;
import models.legacy.LegacyQuiz;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Result;
import play.test.WithApplication;
import services.QuizService;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static play.inject.Bindings.bind;
import static play.test.Helpers.*;


public class QuizControllerTest extends WithApplication {

    private QuizController subject;

    @Mock
    private QuizService mockQuizService;

    @Override
    protected Application provideApplication() {
        MockitoAnnotations.initMocks(this);
        Application application = new GuiceApplicationBuilder()
                .overrides(bind(QuizService.class).toInstance(mockQuizService))
                .build();

        return application;
    }

    @Before
    public void setUp() throws Exception {
        subject = app.injector().instanceOf(QuizController.class);

    }

    @Test
    public void getQuizzesWithUserId_callsServiceWithUserId() throws Exception {
        route(fakeRequest(GET, "/users/21/quizzes?searchStartDate=2017-01-15&searchEndDate=2017-02-15"));

        Map<String, Object> expectParams = Maps.newHashMap();
        expectParams.put("userId", 21);
        expectParams.put("searchStartDate", "2017-01-15");
        expectParams.put("searchEndDate", "2017-02-15");

        verify(mockQuizService).getQuizzes(expectParams);
    }

    @Test
    public void getQuizzesReturnsLegacyQuizList() throws Exception {
        when(mockQuizService.getQuizzes(anyMap())).thenReturn(Arrays.asList(new LegacyQuiz()));

        Result result = route(fakeRequest(GET, "/users/21/quizzes?searchStartDate=2017-01-15&searchEndDate=2017-02-15"));

        String actual = contentAsString(result);

        assertThat(actual).contains("data");
        assertThat(actual).contains("totalCnt");
        assertThat(actual).contains("quizOrderList");
    }
}