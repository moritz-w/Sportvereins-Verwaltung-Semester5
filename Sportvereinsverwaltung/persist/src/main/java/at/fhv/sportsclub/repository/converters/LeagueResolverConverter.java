package at.fhv.sportsclub.repository.converters;

import at.fhv.sportsclub.entity.dept.LeagueEntity;
import at.fhv.sportsclub.repository.dept.DepartmentRepository;
import at.fhv.sportsclub.repository.proxy.DepartmentProxy;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/*
      Created: 12.11.2018
      Author: m_std
      Co-Authors: 
*/
@Component
public class LeagueResolverConverter implements Converter<ObjectId, LeagueEntity> {


    private final DepartmentProxy departmentProxy;

    @Autowired
    public LeagueResolverConverter(DepartmentProxy departmentProxy){
        this.departmentProxy = departmentProxy;
    }

    @Override
    public LeagueEntity convert(ObjectId objectId) {
        return null;
    }
}
