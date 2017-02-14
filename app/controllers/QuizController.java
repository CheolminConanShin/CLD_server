package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import models.Quiz;
import play.mvc.Controller;
import play.mvc.Result;
import services.QuizService;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api
@Singleton
public class QuizController extends Controller{

    @Inject
    private QuizService quizService;

    @ApiOperation("get quiz list with user Id")
    public Result getQuizzes(int userId){
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        List<Quiz> quizzes = quizService.selectQuizList(userId);

        Map<String, List> resultMap = new HashMap<>();
        resultMap.put("quizzes", quizzes);

        return ok(gson.toJson(resultMap));
    }
}
