package at.fhv.sportsclub.controller.converter;

import org.bson.types.ObjectId;
import org.dozer.CustomConverter;
import org.springframework.stereotype.Component;

/*
      Created: 23.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component("objectIdConverter")
public class ObjectIdConverter implements CustomConverter {
    @Override
    public Object convert(Object dest, Object source, Class<?> aClass, Class<?> aClass1) {
        if (source == null) {
            return null;
        }
        if (source instanceof String){
            return new ObjectId((String) source);
        }
        else if (source instanceof ObjectId){
            return ((ObjectId) source).toHexString();
        }
        return null;
    }
}
