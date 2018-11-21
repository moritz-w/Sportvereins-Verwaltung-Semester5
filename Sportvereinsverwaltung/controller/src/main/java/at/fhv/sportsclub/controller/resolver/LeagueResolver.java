package at.fhv.sportsclub.controller.resolver;

import at.fhv.sportsclub.entity.dept.LeagueEntity;
import at.fhv.sportsclub.entity.dept.SportEntity;
import at.fhv.sportsclub.exception.DataAccessException;
import at.fhv.sportsclub.exception.InvalidInputDataException;
import at.fhv.sportsclub.model.dept.LeagueDTO;
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
@Component("leagueResolver")
public class LeagueResolver {

    private static final Logger logger = Logger.getRootLogger();
    private final DepartmentRepository departmentRepository;
    private final Mapper resolverMapper;

    @Autowired
    public LeagueResolver(DepartmentRepository departmentRepository, @Qualifier("resolverMapper") Mapper resolverMapper){
        this.departmentRepository = departmentRepository;
        this.resolverMapper = resolverMapper;
    }

    public LeagueDTO resolveFromObjectId(String id){
        LeagueDTO leagueDTO= null;
        try {
            LeagueEntity leagueEntity = departmentRepository.getLeagueById(id);
            try {
                leagueDTO = resolverMapper.map(leagueEntity, LeagueDTO.class);
            } catch (MappingException e){
                logger.fatal("Mapping from entity to domain failed", e);
            }
        } catch (InvalidInputDataException e) {
            logger.error("Invalid ID input given: ", e);
        } catch (DataAccessException e) {
            logger.warn("Not data could be obtained for the given ID " + id, e);
        }
        return leagueDTO == null ? new LeagueDTO() : leagueDTO;
    }

    /**
     * Returns the complete SportDTO for the given league
     * @param id League id
     * @return Sport where the given league is included
     */
    public SportDTO resolveFromObjectIdFull(String id){
        SportDTO sportDTO = null;
        try {
            SportEntity sportEntity = departmentRepository.getSportByLeagueId(id);
            sportDTO = resolverMapper.map(sportEntity, SportDTO.class);
        } catch (InvalidInputDataException e) {
            logger.error("Invalid ID input given: ", e);
        } catch (DataAccessException e) {
            logger.warn("Not data could be obtained for the given league ID " + id, e);
        } catch (MappingException e){
            logger.fatal("Mapping from entity to domain failed", e);
        }
        return sportDTO == null ? new SportDTO(): sportDTO;
    }

    public ObjectId resolveFromDTO(LeagueDTO dto){
        return new ObjectId(dto.getId());
    }

}
