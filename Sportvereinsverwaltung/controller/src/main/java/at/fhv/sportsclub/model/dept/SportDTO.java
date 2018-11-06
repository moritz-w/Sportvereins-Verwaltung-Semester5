package at.fhv.sportsclub.model.dept;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
public @Data class SportDTO implements Serializable {

    public SportDTO() {}

    private String id;
    private String name;
    @NotNull
    private LeagueDTO[] leagues;
}
