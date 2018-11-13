package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
public @Data class EncounterDTO implements Serializable, IDTO {

    public EncounterDTO() { }

    private String id;

    private LocalDate date;
    private LocalTime time;
    @NotNull
    private ResultDTO homeTeam;
    @NotNull
    private ResultDTO guestTeam;
    private ResponseMessageDTO response;
}
