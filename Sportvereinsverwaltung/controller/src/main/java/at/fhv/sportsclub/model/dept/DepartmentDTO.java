package at.fhv.sportsclub.model.dept;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

import java.io.Serializable;

@AllArgsConstructor
public @Data class DepartmentDTO implements Serializable {

    public DepartmentDTO() { }

    private String id;

    private String deptName;
    private String deptLeade;
    @NotNull
    private SportDTO[] sports;
}
