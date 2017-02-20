package services.transformers;

import models.Quiz;
import models.legacy.LegacyQuiz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class QuizTransformer {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public List<Quiz> transform(List<LegacyQuiz> legacyQuizzes) throws ParseException {
        List<Quiz> quizzes = new ArrayList<>();
        for(LegacyQuiz legacyQuiz: legacyQuizzes){
            quizzes.add(transformOne(legacyQuiz));
        }
        return quizzes;
    }

    private Quiz transformOne(LegacyQuiz legacyQuiz) throws ParseException {
        return new Quiz(legacyQuiz.getActvtImplNid(),
                        legacyQuiz.getActvtImplNm(),
                        legacyQuiz.getQuizImplOdnum(),
                        legacyQuiz.getMkqtQstiFgr(),
                        legacyQuiz.getPrstStsCd(),
                        DATE_FORMAT.parse(legacyQuiz.getAnnoDeliDtm()));
    }
}
