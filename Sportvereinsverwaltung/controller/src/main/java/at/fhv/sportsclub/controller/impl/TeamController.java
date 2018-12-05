package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.controller.common.RequiredPrivileges;
import at.fhv.sportsclub.controller.interfaces.ITeamController;
import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.entity.team.TeamEntity;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.security.AccessLevel;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.sportsclub.repository.team.TeamRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * businessMonkey
 * at.fhv.sportsclub.controller
 * TeamController
 * 07.11.2018 sge
 */
@Service
@Scope("prototype")
public class TeamController extends CommonController<TeamDTO, TeamEntity, TeamRepository> implements ITeamController {

    private TeamRepository teamRepository;
    private static ConcurrentHashMap<String, HashSet<String>> leagueBySportCache = new ConcurrentHashMap<>();       // <SportId, Set<LeagueId>

    private DepartmentController departmentController;

    @Autowired
    public TeamController(TeamRepository repository, DepartmentController departmentController) {
        super(repository, TeamDTO.class, TeamEntity.class);
        this.teamRepository = repository;
        this.departmentController = departmentController;
    }

    //region RMI wrapper methods
    @Override
    @RequiredPrivileges(category = "Team", accessLevel = {AccessLevel.READ})
    public ArrayList<TeamDTO> getAllEntries(SessionDTO session) {
        return new ArrayList<>(this.getAll());
    }

    @Override
    @RequiredPrivileges(category = "Team", accessLevel = {AccessLevel.WRITE})
    public ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, TeamDTO teamDTO) {
        return this.saveOrUpdate(teamDTO);
    }

    @Override
    @RequiredPrivileges(category = "Team", accessLevel = {AccessLevel.READ})
    public TeamDTO getEntryDetails(SessionDTO session, String id){
        return this.getDetails(id, true);
    }

    @Override
    @RequiredPrivileges(category = "Team", accessLevel = {AccessLevel.READ})
    public TeamDTO getById(SessionDTO session, String id){
        return this.getDetails(id, false);
    }

    @Override
    @RequiredPrivileges(category = "Team", accessLevel = {AccessLevel.READ})
    public ListWrapper<TeamDTO> getByLeague(SessionDTO session, String leagueId) {
        ObjectId leagueOId;
        try {
            leagueOId = new ObjectId(leagueId);
        } catch (IllegalArgumentException e){
            return new ListWrapper<>(null, createErrorMessage("Invalid id given"));
        }
        List<TeamDTO> teamDTOS = mapAnyCollection(
                teamRepository.getAllByLeagueEquals(leagueOId), TeamDTO.class, "TeamDTOMappingLight"
        );
        ListWrapper<TeamDTO> teamWrapper = new ListWrapper<>();
        if (teamDTOS.isEmpty()) {
            teamWrapper.setResponse(createErrorMessage("No results could be obtained for the given league"));
        } else {
            teamWrapper.setContents(new ArrayList<>(teamDTOS));
        }
        return teamWrapper;
    }

    @Override
    @RequiredPrivileges(category = "Team", accessLevel = {AccessLevel.READ})
    public ListWrapper<TeamDTO> getBySport(SessionDTO session, String sportId) {
        HashSet<String> leagues;
        if (leagueBySportCache.get(sportId) == null){
            ListWrapper<LeagueDTO> leaguesInSport = departmentController.getLeaguesBySportId(session, sportId);
            if (leaguesInSport.getResponse() != null) {
                return new ListWrapper<>(null, createErrorMessage("Could not obtain data for given sport id"));
            }
            HashSet<String> leagueIds = new HashSet<>();
            for (LeagueDTO leagueDTO : leaguesInSport.getContents()) {
                leagueIds.add(leagueDTO.getId());
            }
            leagueBySportCache.put(sportId, leagueIds);
            leagues = leagueIds;
        } else {
            leagues = leagueBySportCache.get(sportId);
        }
        List<TeamEntity> teamEntities = this.teamRepository.findAll();
        List<TeamEntity> matchingTeams = teamEntities.stream().filter(
                teamEntity -> leagues.contains(teamEntity.getLeague().toHexString())
        ).collect(Collectors.toList());

        return new ListWrapper<>(
                new ArrayList<>(mapAnyCollection(matchingTeams, TeamDTO.class, "TeamDTOMappingLight")), null
        );
    }

    /**
     * Returns a list of teams a person (trainer) 'trains'
     * @param session Session ID
     * @param trainerPersonId The person id of the trainer who's teams are requested
     * @return A list of light mapped teams the given person trains
     */
    @Override
    @RequiredPrivileges(category = "Team", accessLevel = {AccessLevel.READ})
    public ListWrapper<TeamDTO> getTeamsByTrainerId(SessionDTO session, String trainerPersonId){
        ArrayList<PersonEntity> personEntities = new ArrayList<>();
        PersonEntity trainer = new PersonEntity();
        trainer.setId(trainerPersonId);
        personEntities.add(trainer);
        List<TeamEntity> teamsByTrainer = this.teamRepository.getAllByTrainersIsContaining(personEntities);
        if (teamsByTrainer.isEmpty()){
            return new ListWrapper<>(null, createErrorMessage("No team data could be obtained for the given trainer ID"));
        }
        List<TeamDTO> teamDTOMappingLight = mapAnyCollection(teamsByTrainer, TeamDTO.class, "TeamDTOMappingLight");
        return new ListWrapper<>(new ArrayList<>(teamDTOMappingLight), null);
    }

    //endregion

}
