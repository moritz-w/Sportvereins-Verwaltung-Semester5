package at.fhv.sportsclub.entity.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "Person")
@AllArgsConstructor
public @Data class PersonEntity {

    @Id
    private String id;

    private String firstName, lastName;
    private LocalDate dateOfBirth;
    private AddressEntity address;
    private ContactEntity contact;

    public PersonEntity() { }
}
