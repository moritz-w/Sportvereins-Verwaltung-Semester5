package at.fhv.sportsclub.entity.person;

import lombok.Data;
import org.springframework.data.annotation.Id;

public @Data class AddressEntity {
    @Id
    private String id;

    private String street;
    private String zipCode;
    private String city;
}
