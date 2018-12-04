package at.fhv.sportsclub.controller.converter;

import at.fhv.sportsclub.model.security.AccessLevel;
import org.dozer.CustomConverter;
import org.springframework.stereotype.Component;

import java.util.*;

/*
      Created: 18.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component("accessLevelConverter")
public class AccessLevelConverter implements CustomConverter {

    @Override
    public Object convert(Object dest, Object source, Class<?> aClass, Class<?> aClass1) {
        if (source == null) {
            return null;
        }
        if (source instanceof List && !((List) source).isEmpty()){
            if (((List) source).get(0) instanceof String){
                Set<AccessLevel> accessLevels = new HashSet<>();
                for (String lvlStr : (List<String>) source) {
                    accessLevels.add(AccessLevel.getByStr(lvlStr));
                }
                return accessLevels;
            }
        }
        if (source instanceof Set && !((Set) source).isEmpty()) {
            if (((Set) source).iterator().next() instanceof AccessLevel) {
                List<String> lvlStrings = new ArrayList<>();
                for (AccessLevel accessLevel : (Iterable<AccessLevel>) source) {
                    lvlStrings.add(accessLevel.name());
                }
                return lvlStrings;
            }
        }
        return null;
    }

}
