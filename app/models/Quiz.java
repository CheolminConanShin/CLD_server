package models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Quiz {
    String quizID;
    boolean submitted;
    String lastAttemptedDate;
    String sessionNumber;
    String periodStart;
    String periodEnd;
}
