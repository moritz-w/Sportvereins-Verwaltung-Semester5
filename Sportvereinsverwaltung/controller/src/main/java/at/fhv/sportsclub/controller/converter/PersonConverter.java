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

        if (source instanceof List && !((List) source).isEmpty()) {

            if (((List) source).get(0) instanceof String){
                ArrayList<PersonDTO> dtos = new ArrayList<>();
                for (String id :(List<String>)source) {
                    dtos.add(teamMemberResolver.resolveFromObjectId(id));
                }
                return dtos;
            } else if (((List) source).get(0) instanceof PersonDTO){
                ArrayList<String> personIds = new ArrayList<>();
                for (PersonDTO person : (List<PersonDTO>) source) {
                    personIds.add(teamMemberResolver.resolveFromDTO(person));
                }
                return personIds;
            }
        }
        return null;
    }
}
