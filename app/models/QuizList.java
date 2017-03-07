package models;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel
@Getter @AllArgsConstructor
@NoArgsConstructor //used by QuizControllerTest
public class QuizList {
    List<Quiz> quizzes;
}
