package controllers;

import com.google.inject.Guice;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import play.mvc.Result;
import play.test.WithApplication;
import services.QuizService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static play.test.Helpers.*;


public class QuizControllerTest extends WithApplication{

    private QuizController subject;

    @Bind @Mock
    private QuizService mockQuizService;

    @Before
    public void setUp() throws Exception {
        subject = app.injector().instanceOf(QuizController.class);
        MockitoAnnotations.initMocks(this);
        Guice.createInjector(BoundFieldModule.of(this)).injectMembers(subject);
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

    @Test
    public void getQuizzesWithUserId_CallsServiceWithUserId() throws Exception {
        route(fakeRequest(GET, "/users/21/quizzes"));

        verify(mockQuizService).selectQuizList(21);
    }


}