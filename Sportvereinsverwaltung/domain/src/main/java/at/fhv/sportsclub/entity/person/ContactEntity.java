package at.fhv.sportsclub.entity.person;

import at.fhv.sportsclub.entity.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
public @Data class ContactEntity implements CommonEntity {

    @Id
    private String id;

    private String phoneNumber;
    private String emailAddress;

    public ContactEntity() {}
}
