package models;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Date;

@ApiModel
@Getter @AllArgsConstructor @EqualsAndHashCode
@NoArgsConstructor //for QuizControllerTest
public class Quiz {
    int id;
    String name;
    int order;
    int numQuestions;
    String userStatus;
    Date expiration;
}
