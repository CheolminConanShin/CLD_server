package controllers;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import models.Quiz;
import models.QuizList;
import play.mvc.Controller;
import play.mvc.Result;
import services.QuizService;

import javax.inject.Singleton;
import java.text.ParseException;
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

    @ApiOperation(value = "get quiz list with date boundaries",
    response = QuizList.class)
    public Result getQuizzes(@ApiParam(required = true) String searchStartDate, @ApiParam(required = true) String searchEndDate) throws ParseException {

        Map<String, Object> params = ImmutableMap.of("searchStartDate", searchStartDate,
                                                     "searchEndDate", searchEndDate);

        List<Quiz> quizzes = quizService.getQuizzes(params);

        return ok(toJson(new QuizList(quizzes)));
    }
}
