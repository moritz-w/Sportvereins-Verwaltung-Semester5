package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.controller.resolver.LeagueResolver;
import at.fhv.sportsclub.entity.dept.DepartmentEntity;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.DepartmentDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
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
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(
            DepartmentRepository repository,
            @Qualifier("leagueResolver")LeagueResolver leagueResolver
    ){
        super(repository, DepartmentDTO.class, DepartmentEntity.class);
        this.departmentRepository = repository;
        this.leagueResolver = leagueResolver;
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
    public ListWrapper<DepartmentDTO> getAllEntries(SessionDTO session) {
        return new ListWrapper<>(new ArrayList<>(this.getAll()), null);
    }

    @Override
    public ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, DepartmentDTO departmentDTO) {
        return this.saveOrUpdate(departmentDTO);
    }

    @Override
    public ListWrapper<SportDTO> getAllSportEntries(SessionDTO session) {
        return new ListWrapper<>(new ArrayList<>(this.getAllSports("SportEntityMappingLight")), null);
    }

    @Override
    public ListWrapper<SportDTO> getAllSportEntriesFull(SessionDTO session){
        return new ListWrapper<>(new ArrayList<>(this.getAllSports("SportEntityMappingFull")), null);
    }

    @Override
    public SportDTO getSportByLeagueId(SessionDTO session, String leagueId){
        return this.leagueResolver.resolveFromObjectIdFull(leagueId);
    }

    @Override
    public LeagueDTO getLeagueById(SessionDTO session, String id){
        return leagueResolver.resolveFromObjectId(id);
    }
    //endregion
}
