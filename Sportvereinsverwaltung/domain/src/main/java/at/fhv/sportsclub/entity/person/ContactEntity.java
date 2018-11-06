package at.fhv.sportsclub.entity.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
public @Data class ContactEntity {

    @Id
    private String id;

    private String phoneNumber;
    private String emailAddress;
}
