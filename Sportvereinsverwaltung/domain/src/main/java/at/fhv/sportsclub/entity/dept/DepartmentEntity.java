package at.fhv.sportsclub.entity.dept;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Department")
public @Data class DepartmentEntity {

    @Id
    private String id;

    private String deptname, deptLeader;
    private List<SportEntity> sports;
}
