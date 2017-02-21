package controllers;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import models.Quiz;
import models.legacy.NexshopTrainingResponseObject;
import play.mvc.Controller;
import play.mvc.Result;
import services.QuizService;

import javax.inject.Singleton;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static play.libs.Json.toJson;

@Api
@Singleton
public class QuizController extends Controller{

    private QuizService quizService;

    @Inject
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @ApiOperation("get quiz list with date boundaries")
    public Result getQuizzes(String searchStartDate, String searchEndDate) throws ParseException {

        Map<String, Object> params = ImmutableMap.of("searchStartDate", searchStartDate,
                                                     "searchEndDate", searchEndDate);

        List<Quiz> quizzes = quizService.getQuizzes(params);

        return ok(toJson(arrayResponse("quizzes", quizzes)));
    }

    private NexshopTrainingResponseObject<Map<String, Object>> arrayResponse(String key, List<?> objectArray) {
        Map<String, Object> listMap = new HashMap<>();
        listMap.put(key, objectArray);

        return new NexshopTrainingResponseObject<>(listMap);
    }
}
