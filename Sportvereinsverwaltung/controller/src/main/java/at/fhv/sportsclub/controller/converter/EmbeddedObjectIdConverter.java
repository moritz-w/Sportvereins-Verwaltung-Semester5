package at.fhv.sportsclub.controller.converter;

import org.bson.types.ObjectId;
import org.dozer.CustomConverter;
import org.springframework.stereotype.Component;

/*
      Created: 02.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component("embeddedObjectIdConverter")
public class EmbeddedObjectIdConverter implements CustomConverter {

    @Override
    public Object convert(Object dest, Object source, Class<?> aClass, Class<?> aClass1) {
        if (source == null) {
            return new ObjectId().toHexString();
        }
        if (source instanceof String){
            return source;
        }
        return null;
    }
}
