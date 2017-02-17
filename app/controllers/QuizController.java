package controllers;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import models.legacy.LegacyQuiz;
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

    private QuizService quizService;

    @Inject
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @ApiOperation("get quiz list with user Id")
    public Result getQuizzes(int userId, String searchStartDate, String searchEndDate){
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", userId);
        params.put("searchStartDate", searchStartDate);
        params.put("searchEndDate", searchEndDate);

        List<LegacyQuiz> quizzes = quizService.getQuizzes(params);

        Map<String, Object> listMap = new HashMap<>();
        listMap.put("quizOrderList", quizzes);
        listMap.put("totalCnt", quizzes.size());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("data", listMap);

        return ok(gson.toJson(resultMap));
    }
}
