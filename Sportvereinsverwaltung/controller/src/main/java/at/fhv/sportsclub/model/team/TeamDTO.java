package at.fhv.sportsclub.model.team;

import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public @Data class TeamDTO implements Serializable {

    public TeamDTO() { }

    private String id;

    private String name;
    private List<PersonDTO> members;
    private List<PersonDTO> trainers;
    private LeagueDTO league;
    private String type;
}
