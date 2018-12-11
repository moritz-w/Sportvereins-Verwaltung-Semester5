package at.fhv.sportsclub.ejb;

import at.fhv.sportsclub.beanFactory.SpringServiceFactory;
import at.fhv.sportsclub.controller.interfaces.IPersonController;
import at.fhv.sportsclub.controller.interfaces.ITournamentController;
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

    public SpringContextBean() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "factory-beans.xml"
        );
        this.serviceFactory = appContext.getBean("serviceFactory", SpringServiceFactory.class);
    }

    IPersonController getPersonController(){
        return serviceFactory.createPersonController();
    }

    ITournamentController getTournamentController() {
        return serviceFactory.createTournamentController();
    }

    IAuthenticationController getAuthenticationController() {
        return serviceFactory.createAuthenticationController();
    }
}
