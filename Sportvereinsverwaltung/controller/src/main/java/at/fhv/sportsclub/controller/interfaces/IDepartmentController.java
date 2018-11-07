package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.DepartmentDTO;
import at.fhv.sportsclub.model.dept.SportDTO;

import java.rmi.Remote;
import java.util.List;

public interface IDepartmentController extends Remote {

    List<DepartmentDTO> getAllEntries();

    ResponseMessageDTO saveOrUpdateEntry(DepartmentDTO departmentDTO);

    List<SportDTO> getAllSportEntries();
}

