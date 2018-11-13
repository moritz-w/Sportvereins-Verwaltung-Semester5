package at.fhv.sportsclub.repository.converters;

import at.fhv.sportsclub.entity.dept.SportEntity;
import at.fhv.sportsclub.exception.DataAccessException;
import at.fhv.sportsclub.exception.InvalidInputDataException;
import at.fhv.sportsclub.repository.dept.DepartmentRepository;
import at.fhv.sportsclub.repository.proxy.DepartmentProxy;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/*
      Created: 10.11.2018
      Author: Moritz W.
      Co-Authors:
*/
@Component
public class SportsResolverConverter implements Converter<ObjectId, SportEntity> {

    @Autowired
    private DepartmentProxy departmentProxy;

    @Override
    public SportEntity convert(ObjectId objectId) {
        SportEntity sportEntity;
        DepartmentRepository repository = departmentProxy.getDepartmentRepository();
        try {
            sportEntity = repository.getSportById(objectId.toHexString());
        } catch (InvalidInputDataException | DataAccessException e) {
            e.printStackTrace();
            return null;
        }
        return sportEntity;
    }
}
