package at.fhv.sportsclub.entity.dept;

import at.fhv.sportsclub.entity.CommonEntity;
import at.fhv.sportsclub.entity.person.PersonEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Department")
public @Data class DepartmentEntity implements CommonEntity {

    @Id
    private String id;

    private String deptname;
    @DBRef
    private PersonEntity deptLeader;
    private List<SportEntity> sports;
}
