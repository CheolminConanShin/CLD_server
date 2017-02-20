package services.transformers;

import models.Quiz;
import models.legacy.LegacyQuiz;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class QuizTransformerTest {

    private static final int QUIZ_ID = 1;
    private static final String QUIZ_NAME = "name";
    private static final int ORDER_NUMBER = 2;
    private static final String EXPIRATION_DATE = "2017-01-01";
    private static final String STATUS_CODE = "STATUS";
    private static final int NUMBER_OF_QUESTIONS = 3;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    QuizTransformer quizTransformer = new QuizTransformer();

    @Test
    public void shouldTransformLegacyQuizzesListToQuizzesList() throws ParseException {

        List<LegacyQuiz> legacyQuizzes = asList(new LegacyQuiz(QUIZ_ID, QUIZ_NAME, ORDER_NUMBER, NUMBER_OF_QUESTIONS, STATUS_CODE, EXPIRATION_DATE));

        List<Quiz> quizzes = quizTransformer.transform(legacyQuizzes);

        List<Quiz> transformedList = asList(new Quiz(QUIZ_ID, QUIZ_NAME, ORDER_NUMBER, NUMBER_OF_QUESTIONS, STATUS_CODE, DATE_FORMAT.parse(EXPIRATION_DATE)));
        assertThat(transformedList, is(quizzes));
    }


}