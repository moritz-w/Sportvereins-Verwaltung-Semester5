package at.fhv.sportsclub.controller.resolver;

import at.fhv.sportsclub.entity.dept.LeagueEntity;
import at.fhv.sportsclub.exception.DataAccessException;
import at.fhv.sportsclub.exception.InvalidInputDataException;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.repository.dept.DepartmentRepository;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.dozer.MappingException;
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
            return resolveFromObjectId((ObjectId) source);
        }
        else if (source instanceof LeagueDTO){
            return resolveFromDTO((LeagueDTO) source);
        }
        return null;
    }

    private LeagueDTO resolveFromObjectId(ObjectId objectId){
        LeagueDTO leagueDTO= null;
        try {
            LeagueEntity sportEntity = departmentRepository.getLeagueById(objectId.toHexString());
            try {
                leagueDTO = resolverMapper.map(sportEntity, LeagueDTO.class, "SportEntityMappingLight");
            } catch (MappingException e){
                logger.fatal("Mapping from entity to domain failed", e);
            }
        } catch (InvalidInputDataException e) {
            logger.error("Invalid ID input given: ", e);
        } catch (DataAccessException e) {
            logger.warn("Not data could be obtained for the given ID " + objectId.toHexString(), e);
        }
        return leagueDTO == null ? new LeagueDTO() : leagueDTO;
    }

    private ObjectId resolveFromDTO(LeagueDTO dto){
        return new ObjectId(dto.getId());
    }
}
