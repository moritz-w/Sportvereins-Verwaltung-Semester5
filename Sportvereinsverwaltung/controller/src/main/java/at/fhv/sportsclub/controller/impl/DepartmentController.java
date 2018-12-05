package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.controller.common.RequiredPrivileges;
import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.controller.resolver.LeagueResolver;
import at.fhv.sportsclub.controller.resolver.SportsResolver;
import at.fhv.sportsclub.entity.dept.DepartmentEntity;
import at.fhv.sportsclub.entity.dept.SportEntity;
import at.fhv.sportsclub.exception.DataAccessException;
import at.fhv.sportsclub.exception.InvalidInputDataException;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.DepartmentDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.security.AccessLevel;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.repository.dept.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Service
@Scope("prototype")
public class DepartmentController extends CommonController<DepartmentDTO, DepartmentEntity, DepartmentRepository>
        implements IDepartmentController {

    private final LeagueResolver leagueResolver;
    private final SportsResolver sportResolver;
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(
            DepartmentRepository repository,
            @Qualifier("leagueResolver")LeagueResolver leagueResolver,
            @Qualifier("sportsResolver")SportsResolver sportsResolver
            ){
        super(repository, DepartmentDTO.class, DepartmentEntity.class);
        this.departmentRepository = repository;
        this.leagueResolver = leagueResolver;
        this.sportResolver = sportsResolver;
    }

    private List<SportDTO> getAllSports(String mapId) {
        List<SportDTO> allSports = new LinkedList<>();
        List<DepartmentEntity> allDepartments = departmentRepository.findAll();

        for (DepartmentEntity department : allDepartments) {
            allSports.addAll(mapAnyCollection(department.getSports(), SportDTO.class, mapId));
        }
        return allSports;
    }

    //region RMI wrapper methods
    @Override
    @RequiredPrivileges(category = "Department", accessLevel = {AccessLevel.READ})
    public ListWrapper<DepartmentDTO> getAllEntries(SessionDTO session) {
        return new ListWrapper<>(new ArrayList<>(this.getAll()), null);
    }

    @Override
    @RequiredPrivileges(category = "Department", accessLevel = {AccessLevel.WRITE})
    public ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, DepartmentDTO departmentDTO) {
        return this.saveOrUpdate(departmentDTO);
    }

    @Override
    @RequiredPrivileges(category = "Department", accessLevel = {AccessLevel.READ})
    public ListWrapper<SportDTO> getAllSportEntries(SessionDTO session) {
        return new ListWrapper<>(new ArrayList<>(this.getAllSports("SportEntityMappingLight")), null);
    }

    @Override
    @RequiredPrivileges(category = "Department", accessLevel = {AccessLevel.READ})
    public ListWrapper<SportDTO> getAllSportEntriesFull(SessionDTO session){
        return new ListWrapper<>(new ArrayList<>(this.getAllSports("SportEntityMappingFull")), null);
    }

    @Override
    @RequiredPrivileges(category = "Department", accessLevel = {AccessLevel.READ})
    public ListWrapper<LeagueDTO> getLeaguesBySportId(SessionDTO session, String sportId){
        SportDTO sportDTO = sportResolver.resolveFromObjectIdFull(sportId);
        if (sportDTO == null){
            return new ListWrapper<>(null, createErrorMessage("Nothing found for the given sport ID"));
        }
        return new ListWrapper<>(new ArrayList<>(sportDTO.getLeagues()), null);
    }

    @Override
    @RequiredPrivileges(category = "Department", accessLevel = {AccessLevel.READ})
    public SportDTO getSportByLeagueId(SessionDTO session, String leagueId){
        return this.leagueResolver.resolveFromObjectIdFull(leagueId);
    }

    @Override
    @RequiredPrivileges(category = "Department", accessLevel = {AccessLevel.READ})
    public LeagueDTO getLeagueById(SessionDTO session, String id){
        return leagueResolver.resolveFromObjectId(id);
    }

    @Override
    @RequiredPrivileges(category = "Department", accessLevel = {AccessLevel.READ})
    public DepartmentDTO getDepartmentBySportId(SessionDTO session, String sportId){
        DepartmentEntity departmentBySportId = departmentRepository.getDepartmentBySportId(sportId);
        return map(departmentBySportId, DepartmentDTO.class, "DepartmentDTOMappingFull");
    }
    //endregion
}
