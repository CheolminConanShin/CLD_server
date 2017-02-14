package controllers;

import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;
import play.test.WithApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static play.test.Helpers.*;


public class QuizControllerTest extends WithApplication{

    private QuizController subject;

    @Before
    public void setUp() throws Exception {
        subject = app.injector().instanceOf(QuizController.class);
    }

    @Test
    public void getQuizzesReturnsQuizList() throws Exception {
        Result result = route(fakeRequest(GET, "/users/21/quizzes"));

        assertThat(contentAsString(result)).contains("quizzes");
        assertThat(contentAsString(result)).contains("quizID");
        assertThat(contentAsString(result)).contains("submitted");
        assertThat(contentAsString(result)).contains("lastAttemptedDate");
        assertThat(contentAsString(result)).contains("sessionNumber");
        assertThat(contentAsString(result)).contains("periodStart");
        assertThat(contentAsString(result)).contains("periodEnd");
    }
}