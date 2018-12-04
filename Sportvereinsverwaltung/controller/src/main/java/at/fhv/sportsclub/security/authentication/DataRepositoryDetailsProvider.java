package at.fhv.sportsclub.security.authentication;

import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.entity.person.RoleEntity;
import at.fhv.sportsclub.model.security.RoleDTO;
import at.fhv.sportsclub.model.security.UserDetails;
import at.fhv.sportsclub.repository.person.PersonRepository;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component
public class DataRepositoryDetailsProvider implements UserDetailsProvider {

    private final PersonRepository repository;
    private final Mapper mapper;

    @Autowired
    public DataRepositoryDetailsProvider(PersonRepository repository, @Qualifier("generalMapper") Mapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserDetails getUserDetails(String userName) {
        Optional<PersonEntity> entity = repository.findPersonEntityByContact_EmailAddress(userName);
        if (!entity.isPresent()){
            return null;
        }
        PersonEntity personEntity = entity.get();
        List<RoleDTO> roles = new ArrayList<>();
        for (RoleEntity roleEntity : personEntity.getRoles()) {
            roles.add(this.mapper.map(roleEntity, RoleDTO.class, "RoleDTOMapping"));
        }
        return new UserDetails(personEntity.getId(), userName, null, roles);
    }
}
