package at.fhv.sportsclub.model.person;

import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@AllArgsConstructor
public @Data class ContactDTO implements Serializable, IDTO {
    public ContactDTO() {}

    private String id;
    @Pattern(regexp = "^\\+?[0-9\\s]*$",
            message = "Die Telefonnummer darf lediglich aus einem + gefolgt von Zahlen und Leerzeichen bestehen.")
    private String phoneNumber;
    @Email(message = "Die eingegeben E-Mail ist nicht korrekt.")
    private String emailAddress;
    private ResponseMessageDTO response;
}
