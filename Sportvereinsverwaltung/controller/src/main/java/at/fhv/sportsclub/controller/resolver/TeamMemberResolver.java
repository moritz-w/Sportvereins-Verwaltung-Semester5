package at.fhv.sportsclub.controller.resolver;

import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.exception.DataAccessException;
import at.fhv.sportsclub.exception.InvalidInputDataException;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.repository.person.PersonRepository;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
      Created: 05.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component
public class TeamMemberResolver {

    private static final Logger logger = Logger.getRootLogger();
    private final Mapper resolverMapper;
    private final PersonRepository personRepository;

    public TeamMemberResolver(PersonRepository personRepository, @Qualifier("resolverMapper") Mapper resolverMapper){
        this.personRepository = personRepository;
        this.resolverMapper = resolverMapper;
    }

    public PersonDTO resolveFromObjectId(String id){
        return internalResolve(id, "TeamMembersMappingLight");
    }

    public PersonDTO resolveFromObjectIdFull(String id){
        return internalResolve(id, "PersonDTOMappingFull");
    }

    public String resolveFromDTO(PersonDTO person) {
        return person.getId();
    }

    private PersonDTO internalResolve(String id, String mappingID){
        PersonDTO person = null;
        try {
            Optional<PersonEntity> personEntity = this.personRepository.findById(id);
            if (!personEntity.isPresent()){
                throw new DataAccessException("No Person Entity found for given ID");
            }
            try {
                if (mappingID.isEmpty()) {
                    person = resolverMapper.map(personEntity, PersonDTO.class);
                } else {
                    person = resolverMapper.map(personEntity, PersonDTO.class, mappingID);
                }
            } catch (MappingException e){
                logger.fatal("Mapping from entity to domain failed", e);
            }

        }
        catch (DataAccessException e) {
            logger.warn("Not data could be obtained for the given ID " + id, e);
        }
        return person != null ? person : new PersonDTO();
    }

}
