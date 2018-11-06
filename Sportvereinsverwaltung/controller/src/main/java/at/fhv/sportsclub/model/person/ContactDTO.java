package at.fhv.sportsclub.model.person;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@AllArgsConstructor
public @Data class ContactDTO implements Serializable {
    public ContactDTO() {}

    private String id;
    @Pattern(regexp = "", message = "")
    private String phoneNumber;
    @Email(message = "")
    private String emailAddress;
}
