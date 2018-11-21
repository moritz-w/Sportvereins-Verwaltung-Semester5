package at.fhv.sportsclub.controller.converter;

import at.fhv.sportsclub.controller.resolver.LeagueResolver;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.dozer.CustomConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/*
      Created: 17.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component("leagueConverter")
public class LeagueConverter implements CustomConverter {

    private static final Logger logger = Logger.getRootLogger();
    private final LeagueResolver leagueResolver;

    @Autowired
    public LeagueConverter(@Qualifier("leagueResolver") LeagueResolver leagueResolver){
        this.leagueResolver = leagueResolver;
    }

    @Override
    public Object convert(Object dest, Object source, Class<?> aClass, Class<?> aClass1) {
        if(source instanceof ObjectId){
            return leagueResolver.resolveFromObjectId(((ObjectId) source).toHexString());
        }
        else if (source instanceof LeagueDTO){
            return leagueResolver.resolveFromDTO((LeagueDTO) source);
        }
        return null;
    }


}
