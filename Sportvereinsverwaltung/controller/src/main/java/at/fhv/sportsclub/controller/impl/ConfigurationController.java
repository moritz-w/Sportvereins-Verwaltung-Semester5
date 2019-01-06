package at.fhv.sportsclub.controller.impl;

/*
      Created: 04.12.2018
      Author: Moritz W.
      Co-Authors: 
*/

import at.fhv.sportsclub.repository.common.GeneralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationController {

    private final GeneralRepository repository;

    @Autowired
    public ConfigurationController(@Qualifier("generalRepository") GeneralRepository generalRepository){
        this.repository = generalRepository;
    }

    public boolean reloadDatabase(String script){
        return repository.loadScript(script);
    }

    public boolean isDbConnected(){
        return repository.isConnected();
    }
}
