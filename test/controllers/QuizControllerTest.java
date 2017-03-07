package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import models.Quiz;
import models.QuizList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Result;
import play.test.WithApplication;
import services.QuizService;

import java.util.Date;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static play.inject.Bindings.bind;
import static play.test.Helpers.*;


@RunWith(MockitoJUnitRunner.class)
public class QuizControllerTest extends WithApplication {

    @Mock
    private QuizService mockQuizService;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .overrides(bind(QuizService.class).toInstance(mockQuizService))
                .build();
    }

    @Test
    public void getQuizzesWithQueryParameters_callsServiceWithSameParameters() throws Exception {
        route(fakeRequest(GET, "/users/quizzes?searchStartDate=2017-01-15&searchEndDate=2017-02-15"));

        Map<String, Object> expectParams = Maps.newHashMap();
        expectParams.put("searchStartDate", "2017-01-15");
        expectParams.put("searchEndDate", "2017-02-15");

        verify(mockQuizService).getQuizzes(expectParams);
    }

    @Test
    public void getQuizzesReturnsLegacyQuizList() throws Exception {
        Quiz quiz = new Quiz(1, "taco", 1, 1, "taco", new Date());
        when(mockQuizService.getQuizzes(anyMap())).thenReturn(asList(quiz));

        Result result = route(fakeRequest(GET, "/users/quizzes?searchStartDate=2017-01-15&searchEndDate=2017-02-15"));

        QuizList quizList = new ObjectMapper().readValue(contentAsString(result), QuizList.class);

        assertThat(quizList.getQuizzes().size()).isEqualTo(1);
        assertThat(quizList.getQuizzes().get(0).getName()).isEqualTo("taco");
    }
}