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
    @Pattern(regexp = "")
    private String firstName;
    @Pattern(regexp = "")
    private String lastName;

    private LocalDate dateOfBirth;
    @NotNull
    private AddressDTO address;
    @NotNull
    private ContactDTO contact;
}
