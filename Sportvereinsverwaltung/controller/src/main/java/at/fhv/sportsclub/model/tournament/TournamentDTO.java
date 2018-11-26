package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ModificationType;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public @Data class TournamentDTO implements Serializable, IDTO {

    public TournamentDTO() {}

    private static final long serialVersionUID = 111111111267757691L; // changed 20.11.2018

    private String id;

    @Pattern(regexp = "^[-\\w\\s]+$", message = "A tournament may only contain word characters including whitespaces, dashes and underscores")
    private String name;
    private String league;  // object id
    private String leagueName;
    private String sportsName;
    private List<EncounterDTO> encounters;
    private List<ParticipantDTO> teams;
    private ResponseMessageDTO response;
    private ModificationType modificationType;
}
