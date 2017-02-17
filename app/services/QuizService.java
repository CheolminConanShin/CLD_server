package services;

import apis.QuizLegacyConnector;
import com.google.inject.Inject;
import models.legacy.LegacyQuiz;

import java.util.List;
import java.util.Map;

public class QuizService {

    private QuizLegacyConnector quizLegacyConnector;

    @Inject
    public QuizService(QuizLegacyConnector quizLegacyConnector) {
        this.quizLegacyConnector = quizLegacyConnector;
    }

    public List<LegacyQuiz> getQuizzes(Map<String, Object> params) {
        List<LegacyQuiz> legacyQuizzes = quizLegacyConnector.selectQuizList(params);
        //transform
        return legacyQuizzes;
    }

}
