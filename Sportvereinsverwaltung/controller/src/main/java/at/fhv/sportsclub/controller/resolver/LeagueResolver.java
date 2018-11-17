package at.fhv.sportsclub.controller.resolver;

import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.repository.dept.DepartmentRepository;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/*
      Created: 17.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component("leagueResolver")
public class LeagueResolver implements CustomConverter {

    private static final Logger logger = Logger.getRootLogger();
    private final DepartmentRepository departmentRepository;
    private final Mapper resolverMapper;

    @Autowired
    public LeagueResolver(DepartmentRepository departmentRepository, @Qualifier("resolverMapper") Mapper resolverMapper){
        this.departmentRepository = departmentRepository;
        this.resolverMapper = resolverMapper;
    }

    @Override
    public Object convert(Object dest, Object source, Class<?> aClass, Class<?> aClass1) {
        if(source instanceof ObjectId){
            // resolve from database over repo
        }
        else if (source instanceof LeagueDTO){
            return resolveFromDTO((LeagueDTO) source);
        }
        return null;
    }

    private ObjectId resolveFromDTO(LeagueDTO dto){
        return new ObjectId(dto.getId());
    }
}
