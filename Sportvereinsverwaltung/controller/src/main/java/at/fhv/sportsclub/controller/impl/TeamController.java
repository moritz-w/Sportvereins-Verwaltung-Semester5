package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.controller.interfaces.ITeamController;
import at.fhv.sportsclub.entity.team.TeamEntity;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.sportsclub.repository.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    @Autowired
    public TeamController(TeamRepository repository) {
        super(repository, TeamDTO.class, TeamEntity.class);
        this.teamRepository = repository;
    }

    //region RMI wrapper methods
    @Override
    public ArrayList<TeamDTO> getAllEntries() {
        return new ArrayList<>(this.getAll());
    }

    @Override
    public ResponseMessageDTO saveOrUpdateEntry(TeamDTO teamDTO) {
        return this.saveOrUpdate(teamDTO);
    }
    //endregion

}
