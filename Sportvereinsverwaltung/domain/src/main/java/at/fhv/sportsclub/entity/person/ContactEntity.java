package at.fhv.sportsclub.entity.person;

import lombok.Data;
import org.springframework.data.annotation.Id;

public @Data class ContactEntity {

    @Id
    private String id;

    private String phoneNumber;
    private String emailAddress;
}
