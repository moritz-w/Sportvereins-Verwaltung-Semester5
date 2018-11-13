package at.fhv.sportsclub.model.dept;

import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@AllArgsConstructor
public @Data class LeagueDTO implements Serializable, IDTO {

    public LeagueDTO() {}

    private static final long serialVersionUID = 1109685098267757690L;

    private String id;
    private String name;
    private ResponseMessageDTO response;
}
