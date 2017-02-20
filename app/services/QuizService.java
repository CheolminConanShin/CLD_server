package services;

import apis.QuizLegacyConnector;
import com.google.inject.Inject;
import models.Quiz;
import models.legacy.LegacyQuiz;
import services.transformers.QuizTransformer;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class QuizService {

    private QuizLegacyConnector quizLegacyConnector;

    private QuizTransformer quizTransformer;

    @Inject
    public QuizService(QuizLegacyConnector quizLegacyConnector, QuizTransformer quizTransformer) {
        this.quizLegacyConnector = quizLegacyConnector;
        this.quizTransformer = quizTransformer;
    }

    public List<Quiz> getQuizzes(Map<String, Object> params) throws ParseException {
        List<LegacyQuiz> legacyQuizzes = quizLegacyConnector.selectQuizList(params);
        return quizTransformer.transform(legacyQuizzes);
    }

}
