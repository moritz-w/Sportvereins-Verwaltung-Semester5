package at.fhv.sportsclub.beanFactory;

import at.fhv.sportsclub.controller.impl.DepartmentController;
import at.fhv.sportsclub.controller.impl.PersonController;
import at.fhv.sportsclub.controller.impl.TeamController;
import at.fhv.sportsclub.controller.impl.TournamentController;
import at.fhv.sportsclub.controller.interfaces.IMessageController;
import at.fhv.sportsclub.security.authentication.AuthenticationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
      Created: 11.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component("serviceFactory")
@Scope("singleton")
public class SpringServiceFactory {

    private AuthenticationController authenticationController;

    @Autowired
    public SpringServiceFactory(AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;
    }

    @Lookup
    public PersonController createPersonController() {
        return null;
    }
    @Lookup
    public DepartmentController createDepartmentController() {
        return null;
    }
    @Lookup
    public TeamController createTeamController() {
        return null;
    }
    @Lookup
    public TournamentController createTournamentController(){
        return null;
    }

    public AuthenticationController createAuthenticationController() {
        return this.authenticationController;
    }

    @Lookup
    public IMessageController createMessageController() {
        return null;
    }
}
