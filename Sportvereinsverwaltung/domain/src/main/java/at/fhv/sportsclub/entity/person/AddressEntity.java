package at.fhv.sportsclub.entity.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
public @Data class AddressEntity {
    @Id
    private String id;

    private String street;
    private String zipCode;
    private String city;

    public AddressEntity() { }
}
