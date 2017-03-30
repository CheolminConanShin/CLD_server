package controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import models.Image;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;

import static play.libs.Json.toJson;

@Api("Image API")
public class ImageController extends Controller {

    public static final String BEAUTYANDTHEBEAST = "beautyandthebeast";
    private static final String ALADIN = "aladin";

    @ApiOperation(value = "get quiz list with date boundaries", response = Image.class)
    public Result sendImageResponse(String imageName) throws IOException {
        Image responseImage = new Image("Does not exist", "You are Wrong!!");
        if(imageName.equals(BEAUTYANDTHEBEAST)){
            responseImage = new Image("Beauty and the Beast", "beautyandthebeast");
        }else if (imageName.equals(ALADIN)){
            responseImage = new Image("Aladin", "aladin");
        }
        return ok(toJson(responseImage));
    }
}
