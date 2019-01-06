package at.fhv.sportsclub.ejb;

import at.fhv.sportsclub.beanFactory.SpringServiceFactory;
import at.fhv.sportsclub.controller.interfaces.*;
import at.fhv.sportsclub.security.authentication.IAuthenticationController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
      Created: 11.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
public class SpringContextBean {

    private SpringServiceFactory serviceFactory;

    SpringContextBean() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "factory-beans.xml"
        );
        this.serviceFactory = appContext.getBean("serviceFactory", SpringServiceFactory.class);
    }

    public IPersonController getPersonController(){
        return serviceFactory.createPersonController();
    }

    public ITournamentController getTournamentController() {
        return serviceFactory.createTournamentController();
    }

    public ITeamController getTeamController() {
        return serviceFactory.createTeamController();
    }

    public IDepartmentController getDepartmentController() {
        return serviceFactory.createDepartmentController();
    }

    public IAuthenticationController getAuthenticationController() {
        return serviceFactory.createAuthenticationController();
    }

    public IMessageController getMessageController() {
        return serviceFactory.createMessageController();
    }
}
