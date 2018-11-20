package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public @Data class TournamentDTO implements Serializable, IDTO {

    public TournamentDTO() {}

    private static final long serialVersionUID = 111111111267757691L; // changed 20.11.2018

    private String id;

    private String name;
    private LeagueDTO league;
    @NotNull
    private List<EncounterDTO> encounters;
    @NotNull
    private List<ParticipantDTO> teams;
    private ResponseMessageDTO response;
}
