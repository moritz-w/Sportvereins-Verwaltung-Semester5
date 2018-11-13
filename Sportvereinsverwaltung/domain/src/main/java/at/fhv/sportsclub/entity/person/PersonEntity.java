package at.fhv.sportsclub.entity.person;

import at.fhv.sportsclub.entity.CommonEntity;
import at.fhv.sportsclub.entity.dept.SportEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "Person")
@AllArgsConstructor
public @Data class PersonEntity implements CommonEntity {

    @Id
    private String id;

    private String firstName, lastName;
    private LocalDate dateOfBirth;
    private AddressEntity address;
    private ContactEntity contact;
    @DBRef
    private List<SportEntity> sports;

    public PersonEntity() { }
}
