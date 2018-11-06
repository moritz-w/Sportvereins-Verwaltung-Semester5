package at.fhv.sportsclub.controller;


import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.entity.dept.DepartmentEntity;
import at.fhv.sportsclub.model.dept.DepartmentDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.repository.dept.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class DepartmentController extends CommonController<DepartmentDTO, DepartmentEntity, DepartmentRepository> {

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(DepartmentRepository repository) {
        super(repository, DepartmentDTO.class, DepartmentEntity.class);
        this.departmentRepository = repository;
    }

    @Override
    protected String getId(DepartmentEntity entity) {
        return entity.getId();
    }

    public List<SportDTO> getAllSports() {
        List<SportDTO> allSports = new LinkedList<>();
        List<DepartmentEntity> allDepartments = departmentRepository.findAll();

        for (DepartmentEntity department : allDepartments) {
            allSports.addAll(mapAnyCollection(department.getSports(), SportDTO.class, "SportEntityMapping"));
        }
        return allSports;
    }
}
