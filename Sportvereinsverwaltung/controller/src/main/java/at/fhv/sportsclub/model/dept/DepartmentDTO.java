package at.fhv.sportsclub.model.dept;

import at.fhv.sportsclub.model.person.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public @Data class DepartmentDTO implements Serializable {

    public DepartmentDTO() { }

    private String id;

    private String deptName;
    private PersonDTO deptLeader;
    @NotNull
    private List<SportDTO> sports;
}
