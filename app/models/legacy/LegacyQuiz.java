package models.legacy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //used by Gson for deserialization
public class LegacyQuiz {
    private static final long serialVersionUID = 1L;

    private Integer actvtImplNid;
    private String actvtImplNm;
    private Integer quizImplOdnum;
    private Integer mkqtQstiFgr;
    private String prstStsCd;
    private String annoDeliDtm;
}
