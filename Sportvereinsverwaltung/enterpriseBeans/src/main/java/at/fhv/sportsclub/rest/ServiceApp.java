package at.fhv.sportsclub.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/*
      Created: 11.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@ApplicationPath("rs-service")
public class ServiceApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resourceClasses = new HashSet<>();
        resourceClasses.add(AuthenticationServiceBean.class);
        resourceClasses.add(TournamentServiceBean.class);
        return resourceClasses;
    }
}
