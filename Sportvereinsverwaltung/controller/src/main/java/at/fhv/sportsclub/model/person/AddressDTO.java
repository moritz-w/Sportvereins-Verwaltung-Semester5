package at.fhv.sportsclub.model.person;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public @Data class AddressDTO implements Serializable {

    private String id;
    @Pattern(regexp = "", message = "")
    private String street;
    @Pattern(regexp = "", message = "")
    private String zipCode;
    // optional TODO: implement custom validator to geo lookup city names
    @Pattern(regexp = "", message = "")
    private String city;
}
