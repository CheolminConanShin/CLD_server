package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class HomeExampleController extends Controller {

    public Result index() {
        return ok(index.render("Nexshop Traning API Proxy is ready."));
    }

}
