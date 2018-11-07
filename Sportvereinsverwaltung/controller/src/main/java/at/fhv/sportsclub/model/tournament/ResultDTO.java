package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.team.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
public @Data class ResultDTO implements Serializable {

    public ResultDTO() { }

    private String id;

    private TeamDTO team;
    private int points;
}
