package at.fhv.sportsclub.repository.converters;

import at.fhv.sportsclub.entity.dept.SportEntity;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/*
      Created: 10.11.2018
      Author: m_std
      Co-Authors:
*/
@Component
public class SportsResolverConverter implements Converter<ObjectId, SportEntity> {

    @Override
    public SportEntity convert(ObjectId objectId) {
        System.out.println(objectId.toHexString());
        // TODO: filter and return sport entities from department
        // -> combination of query and interface based projection could be used
        // mongo shell example: db.Department.find({"sports._id": ObjectId("5be617a79772fb0c5d2fadc9")}, {"sports.$": 1}).pretty()
        // translate to query
        return null;
    }
}
