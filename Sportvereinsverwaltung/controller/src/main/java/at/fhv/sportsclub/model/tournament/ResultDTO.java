package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
public @Data class ResultDTO implements Serializable, IDTO {

    public ResultDTO() { }

    private String id;

    private TeamDTO team;
    private int points;
    private ResponseMessageDTO response;
}
