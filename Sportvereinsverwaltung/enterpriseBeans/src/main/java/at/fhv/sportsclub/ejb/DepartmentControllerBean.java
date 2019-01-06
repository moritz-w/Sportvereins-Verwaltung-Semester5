package at.fhv.sportsclub.ejb;

import at.fhv.sportsclub.ejb.interfaces.IDepartmentController;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.DepartmentDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.security.SessionDTO;

import javax.ejb.Stateless;
import java.rmi.RemoteException;

/*
      Created: 18.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Stateless
public class DepartmentControllerBean implements IDepartmentController {

    private at.fhv.sportsclub.controller.interfaces.IDepartmentController departmentController;

    public DepartmentControllerBean() {
        SpringContextBean contextBean = SpringContextBeanFactory.getInstance();
        this.departmentController = contextBean.getDepartmentController();
    }

    @Override
    public ListWrapper<DepartmentDTO> getAllEntries(SessionDTO session) {
        try {
            return departmentController.getAllEntries(session);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, DepartmentDTO departmentDTO) {
        try {
            return departmentController.saveOrUpdateEntry(session, departmentDTO);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ListWrapper<SportDTO> getAllSportEntries(SessionDTO session) {
        try {
            return departmentController.getAllSportEntries(session);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ListWrapper<SportDTO> getAllSportEntriesFull(SessionDTO session) {
        try {
            return departmentController.getAllSportEntriesFull(session);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SportDTO getSportByLeagueId(SessionDTO session, String leagueId) {
        try {
            return departmentController.getSportByLeagueId(session, leagueId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LeagueDTO getLeagueById(SessionDTO session, String id) {
        try {
            return departmentController.getLeagueById(session, id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ListWrapper<LeagueDTO> getLeaguesBySportId(SessionDTO session, String sportId) {
        try {
            return departmentController.getLeaguesBySportId(session, sportId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DepartmentDTO getDepartmentBySportId(SessionDTO session, String sportId) {
        try {
            return departmentController.getDepartmentBySportId(session, sportId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
