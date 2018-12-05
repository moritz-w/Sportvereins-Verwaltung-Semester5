package at.fhv.sportsclub.repository.common;

import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/*
      Created: 04.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Repository
@Qualifier("generalRepository")
public interface GeneralRepository extends CustomGeneralRepository, CommonRepository<PersonEntity, String> {
}
