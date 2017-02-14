package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import models.Quiz;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api
@Singleton
public class QuizController extends Controller{

    @ApiOperation("get quiz list with user Id")
    public Result getQuizzes(int id){
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(new Quiz());
        Map<String, List> resultMap = new HashMap<>();
        resultMap.put("quizzes", quizzes);
        return ok(gson.toJson(resultMap));
    }
}
