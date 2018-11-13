package at.fhv.sportsclub.controller;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.entity.dept.DepartmentEntity;
import at.fhv.sportsclub.entity.dept.SportEntity;
import at.fhv.sportsclub.exception.InvalidInputDataException;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.DepartmentDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.repository.dept.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Service
@Scope("prototype")
public class DepartmentController extends CommonController<DepartmentDTO, DepartmentEntity, DepartmentRepository>
        implements IDepartmentController {

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(DepartmentRepository repository) {
        super(repository, DepartmentDTO.class, DepartmentEntity.class);
        this.departmentRepository = repository;
    }

    public List<SportDTO> getAllSports() {
        List<SportDTO> allSports = new LinkedList<>();
        List<DepartmentEntity> allDepartments = departmentRepository.findAll();

        for (DepartmentEntity department : allDepartments) {
            allSports.addAll(mapAnyCollection(department.getSports(), SportDTO.class, "SportEntityMapping"));
        }
        return allSports;
    }

    //region RMI wrapper methods
    @Override
    public ArrayList<DepartmentDTO> getAllEntries() {
        return new ArrayList<>(this.getAll());
    }

    @Override
    public ResponseMessageDTO saveOrUpdateEntry(DepartmentDTO departmentDTO) {
        return this.saveOrUpdate(departmentDTO);
    }

    @Override
    public ArrayList<SportDTO> getAllSportEntries() {
        return new ArrayList<>(this.getAllSports());
    }
    //endregion
}
