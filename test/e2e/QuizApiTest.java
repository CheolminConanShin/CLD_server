package e2e;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import models.legacy.LegacyQuiz;
import models.legacy.NexshopTrainingResponseObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mbtest.javabank.Client;
import org.mbtest.javabank.fluent.ImposterBuilder;
import org.mbtest.javabank.http.imposters.Imposter;

import play.core.j.JavaResultExtractor;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;

public class QuizApiTest extends WithApplication {

    private Client client;

    @Before
    public void setUp() throws Exception {
        client = new Client();
    }

    @Test
    public void getQuizzesWithQueryParameter_callsStubServer() throws Exception {
        stubAPIResponseForNumberOfQuizzes(10);

        Http.RequestBuilder requestBuilder = fakeRequest(GET, "/users/quizzes?searchStartDate=2017-01-15&searchEndDate=2017-02-15");
        requestBuilder.header("Edt-Server-Domain","http://localhost:3000");
        Result result = route(requestBuilder);

        assertThat(result.status()).isEqualTo(200);
        byte[] body = JavaResultExtractor.getBody(result, 0L);
        String bodyStr = new String(body, "utf-8");
        JsonNode bodyJson = Json.parse(bodyStr);

        assertThat(bodyJson.get("data")).isNotNull();
        assertThat(bodyJson.get("data").get("quizzes")).isNotNull();
        assertThat(bodyJson.get("data").get("quizzes").size()).isEqualTo(10);
    }

    @After
    public void tearDown() throws Exception {
        client.deleteImposter(3000);
    }

    public void stubAPIResponseForNumberOfQuizzes(int numberOfQuizzes) throws IOException {
        List<LegacyQuiz> quizList = createQuizList(numberOfQuizzes);
        JsonNode data = Json.newObject().set("quizOrderList", Json.toJson(quizList));
        NexshopTrainingResponseObject<JsonNode> responseObject = new NexshopTrainingResponseObject<>(data);

        Imposter imposter = ImposterBuilder.anImposter()
                .onPort(3000)
                .stub()
                .response()
                .is()
                .body(new ObjectMapper().writeValueAsString(responseObject))
                .statusCode(200)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .end()
                .end()
                .end()
                .build();
        client.createImposter(imposter);
    }

    private List<LegacyQuiz> createQuizList(int numberOfQuizzes) {
        List<LegacyQuiz> quizzes = new ArrayList<>();
        for (int i = 0; i < numberOfQuizzes; i++) {
            quizzes.add(new LegacyQuiz(1, "test", 1, 1, "STS", "2017-01-01"));
        }
        return quizzes;
    }
}
