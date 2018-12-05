package at.fhv.sportsclub.repository.common;

import at.fhv.sportsclub.repository.CommonRepository;
import org.springframework.stereotype.Repository;

/*
      Created: 04.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Repository
public interface CustomGeneralRepository {
    boolean loadScript(String scriptFile);
    boolean isConnected();
}
