package controllers;

import org.junit.Test;
import play.mvc.Result;
import play.test.WithApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static play.test.Helpers.*;

public class HomeExampleControllerTest extends WithApplication {

    @Test
    public void fakeRequestHome_renderWelcomeMessage() throws Exception {
        Result result = route(fakeRequest(GET, "/"));

        assertThat(contentAsString(result)).contains("Nexshop Traning API Proxy is ready");
    }
}