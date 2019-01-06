package at.fhv.sportsclub.controller.converter;

import at.fhv.sportsclub.controller.impl.PersonController;
import at.fhv.sportsclub.controller.resolver.TeamMemberResolver;
import at.fhv.sportsclub.model.person.PersonDTO;
import org.bson.types.ObjectId;
import org.dozer.CustomConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
      Created: 05.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component("personConverter")
public class PersonConverter implements CustomConverter {

    private final TeamMemberResolver teamMemberResolver;

    @Autowired
    public PersonConverter(TeamMemberResolver teamMemberResolver){
        this.teamMemberResolver = teamMemberResolver;
    }

    @Override
    public Object convert(Object destination, Object source, Class<?> aClass, Class<?> aClass1) {
        if (source == null) {
            return null;
        }

        if (source instanceof List) {
            if (((List) source).isEmpty()){
                return new ArrayList<>();
            }
            if (((List) source).get(0) instanceof ObjectId){
                ArrayList<PersonDTO> dtos = new ArrayList<>();
                for (ObjectId id :(List<ObjectId>)source) {
                    dtos.add(teamMemberResolver.resolveFromObjectId(id));
                }
                return dtos;
            } else if (((List) source).get(0) instanceof PersonDTO){
                ArrayList<ObjectId> personIds = new ArrayList<>();
                for (PersonDTO person : (List<PersonDTO>) source) {
                    personIds.add(teamMemberResolver.resolveFromDTO(person));
                }
                return personIds;
            }
        } else if (source instanceof PersonDTO){
            return teamMemberResolver.resolveFromDTO((PersonDTO) source);
        } else if (source instanceof ObjectId) {
            return teamMemberResolver.resolveFromObjectId((ObjectId) source);
        }
        return null;
    }
}
