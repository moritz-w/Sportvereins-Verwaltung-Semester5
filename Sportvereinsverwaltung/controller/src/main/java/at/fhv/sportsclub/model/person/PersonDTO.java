package at.fhv.sportsclub.model.person;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
public @Data class PersonDTO implements Serializable {

    public PersonDTO() {}

    private String id;
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Im Vornamen dürfen keine Zahlen oder Sonderzeichen vorkommen.")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Im Nachnamen dürfen keine Zahlen oder Sonderzeichen vorkommen.")
    private String lastName;

    private LocalDate dateOfBirth;
    @NotNull
    private AddressDTO address;
    @NotNull
    private ContactDTO contact;
}
