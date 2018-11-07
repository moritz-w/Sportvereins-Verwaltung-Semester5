package at.fhv.sportsclub.model.dept;

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
    private String deptLeader;
    @NotNull
    private List<SportDTO> sports;
}
