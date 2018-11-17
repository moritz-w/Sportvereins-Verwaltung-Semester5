package at.fhv.sportsclub.model.person;

import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public @Data class PersonDTO implements Serializable, IDTO {

    public PersonDTO() {}

    private static final long serialVersionUID = 5529685098267757690L;

    private String id;
    @Pattern(regexp = "^[a-zA-ZäöüÄÖÜ\\s]*$", message = "Im Vornamen dürfen keine Zahlen oder Sonderzeichen vorkommen.")
    private String firstName;
    @Pattern(regexp = "^[a-zA-ZäöüÄÖÜ\\s]*$", message = "Im Nachnamen dürfen keine Zahlen oder Sonderzeichen vorkommen.")
    private String lastName;

    private LocalDate dateOfBirth;
    @NotNull
    private AddressDTO address;
    @NotNull
    private ContactDTO contact;
    private List<SportDTO> sports;

    private ResponseMessageDTO response = null;
}
