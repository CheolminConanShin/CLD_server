package models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode
public class Quiz {
    int id;
    String name;
    int order;
    int numQuestions;
    String userStatus;
    Date expiration;
}
