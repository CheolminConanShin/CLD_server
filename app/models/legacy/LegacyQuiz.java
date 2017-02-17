package models.legacy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LegacyQuiz {

    private static final long serialVersionUID = 1L;

    private Integer actvtImplNid;
    private String actvtImplNm;
    private Integer quizImplOdnum;
    private String annoDeliDtm;
    private String prstStsCd;
    private Integer mkqtQstiFgr;
}
