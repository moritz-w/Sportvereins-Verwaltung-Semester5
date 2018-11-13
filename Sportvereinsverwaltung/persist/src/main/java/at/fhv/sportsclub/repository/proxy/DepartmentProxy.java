package at.fhv.sportsclub.repository.proxy;

import at.fhv.sportsclub.repository.dept.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/*
      Created: 13.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component
public class DepartmentProxy {

    private static DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentProxy(@Lazy DepartmentRepository departmentRepository){
        DepartmentProxy.departmentRepository = departmentRepository;
    }

    public DepartmentRepository getDepartmentRepository() {
        return departmentRepository;
    }
}
