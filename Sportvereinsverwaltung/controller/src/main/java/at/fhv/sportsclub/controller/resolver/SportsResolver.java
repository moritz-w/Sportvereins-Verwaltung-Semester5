package at.fhv.sportsclub.controller.resolver;

import at.fhv.sportsclub.entity.dept.SportEntity;
import at.fhv.sportsclub.exception.DataAccessException;
import at.fhv.sportsclub.exception.InvalidInputDataException;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.repository.dept.DepartmentRepository;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/*
      Created: 20.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component("sportsResolver")
public class SportsResolver {

    private static final Logger logger = Logger.getRootLogger();
    private final DepartmentRepository departmentRepository;
    private final Mapper resolverMapper;

    @Autowired
    public SportsResolver(DepartmentRepository departmentRepository, @Qualifier("resolverMapper") Mapper resolverMapper){
        this.departmentRepository = departmentRepository;
        this.resolverMapper = resolverMapper;
    }

    public SportDTO resolveFromObjectId(String id){
        return internalResolve(id, "SportEntityMappingLight");
    }

    public SportDTO resolveFromObjectIdFull(String id){
        return internalResolve(id, "SportEntityMappingFull");
    }

    public ObjectId resolveFromDTO(SportDTO dto){
        return new ObjectId(dto.getId());
    }

    private SportDTO internalResolve(String id, String mappingID){
        SportDTO sportDTO = null;
        try {
            SportEntity sportEntity = departmentRepository.getSportById(id);
            try {
                if (mappingID.isEmpty()) {
                    sportDTO = resolverMapper.map(sportEntity, SportDTO.class);
                } else {
                    sportDTO = resolverMapper.map(sportEntity, SportDTO.class, mappingID);
                }
            } catch (MappingException e){
                logger.fatal("Mapping from entity to domain failed", e);
            }
        } catch (InvalidInputDataException e) {
            logger.error("Invalid ID input given: ", e);
        } catch (DataAccessException e) {
            logger.warn("Not data could be obtained for the given ID " + id, e);
        }
        return sportDTO == null ? new SportDTO() : sportDTO;
    }

}
