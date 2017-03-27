package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;

public class ImageController extends Controller {
    public Result imageA() throws IOException {
        return ok("https://s3.amazonaws.com/applause-devmktg/2015/12/02/4aj6bzx1oe_agile.png").withHeader("Access-Control-Allow-Origin", "*");
    }

    public Result imageB() throws IOException {
        return ok("https://i.ytimg.com/vi/DCr4P0KU1NE/maxresdefault.jpg").withHeader("Access-Control-Allow-Origin", "*");
    }
}
