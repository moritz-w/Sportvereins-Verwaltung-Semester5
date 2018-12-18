package at.fhv.sportsclub.ejb;

import at.fhv.sportsclub.ejb.interfaces.ITeamController;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.team.TeamDTO;

import javax.ejb.Stateless;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*
      Created: 18.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Stateless
public class TeamControllerBean implements ITeamController {

    private at.fhv.sportsclub.controller.interfaces.ITeamController teamController;

    public TeamControllerBean(){
        SpringContextBean contextBean = SpringContextBeanFactory.getInstance();
        this.teamController = contextBean.getTeamController();
    }

    @Override
    public ArrayList<TeamDTO> getAllEntries(SessionDTO session) {
        try {
            return teamController.getAllEntries(session);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, TeamDTO teamDTO) {
        try {
            return teamController.saveOrUpdateEntry(session, teamDTO);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TeamDTO getById(SessionDTO session, String id) {
        try {
            teamController.getById(session, id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TeamDTO getEntryDetails(SessionDTO session, String id) {
        try {
            return teamController.getEntryDetails(session, id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ListWrapper<TeamDTO> getByLeague(SessionDTO session, String leagueId) {
        try {
            return teamController.getByLeague(session, leagueId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ListWrapper<TeamDTO> getBySport(SessionDTO session, String sportId) {
        try {
            return teamController.getBySport(session, sportId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ListWrapper<TeamDTO> getTeamsByTrainerId(SessionDTO session, String trainerPersonId) {
        try {
            return teamController.getTeamsByTrainerId(session, trainerPersonId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
