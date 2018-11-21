package at.fhv.sportsclub.repository.dept;

import at.fhv.sportsclub.entity.dept.LeagueEntity;
import at.fhv.sportsclub.entity.dept.SportEntity;
import at.fhv.sportsclub.exception.DataAccessException;
import at.fhv.sportsclub.exception.InvalidInputDataException;

/*
      Created: 12.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public interface CustomDepartmentRepository {
    SportEntity getSportById(String id) throws InvalidInputDataException, DataAccessException;
    SportEntity getSportByLeagueId(String id) throws InvalidInputDataException, DataAccessException;
    LeagueEntity getLeagueById(String id) throws InvalidInputDataException, DataAccessException;
}
