package at.fhv.sportsclub.model.person;

import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@AllArgsConstructor
public @Data class AddressDTO implements Serializable, IDTO {

    private static final long serialVersionUID = 1111685098267757690L;

    private String id;
    @Pattern(regexp = "^[a-zA-Z0-9äöüÄÖÜ\\s-]*$",
            message = "Der Straßenname darf lediglich aus Buchstaben, Zahlen, Leerzeichen und \"-\" bestehen.")
    private String street;
    @Pattern(regexp = "^[a-zA-Z0-9äöüÄÖÜ\\s-]*$",
            message = "Die Postleitzahl darf lediglich aus Buchstaben, Zahlen, Leerzeichen und \"-\" bestehen.")
    private String zipCode;
    // optional TODO: implement custom validator to geo lookup city names
    @Pattern(regexp = "", message = "")
    private String city;

    private ResponseMessageDTO response;

    public AddressDTO() { }
}
