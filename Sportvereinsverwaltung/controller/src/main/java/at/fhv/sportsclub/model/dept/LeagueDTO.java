package at.fhv.sportsclub.model.dept;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@AllArgsConstructor
public @Data class LeagueDTO implements Serializable {

    public LeagueDTO() {}

    private String id;
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Im Vornamen dürfen keine Zahlen oder Sonderzeichen vorkommen.")
    private String name;
}
