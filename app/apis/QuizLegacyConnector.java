package apis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.legacy.LegacyQuiz;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WSRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class QuizLegacyConnector extends LegacyConnector {

    public List<LegacyQuiz> selectQuizList(Map<String, Object> params) {
        WSRequest request = makeRequest("/act/qiz/tkn/TakeMob/selectQuizList.mob");

        JsonNode json = Json.newObject()
                .put("srhStrDtm", (String) params.get("searchStartDate"))
                .put("srhEndDtm", (String) params.get("searchEndDate"));

        F.Promise<List<LegacyQuiz>> listPromise = request.post(json).map(response -> {
            JsonNode jsonNode = response.asJson();
            JsonNode data = jsonNode.get("data");
            ArrayNode quizOrderList = (ArrayNode) data.get("quizOrderList");

            ObjectMapper mapper = new ObjectMapper();

            return Arrays.asList(mapper.readValue(quizOrderList.toString(), LegacyQuiz[].class));
        });

        List<LegacyQuiz> quizzes = listPromise.get(30000);

        return quizzes;
    }

}
