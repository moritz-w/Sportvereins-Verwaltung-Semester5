package at.fhv.sportsclub.repository.team;

import at.fhv.sportsclub.entity.team.TeamEntity;
import at.fhv.sportsclub.repository.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * businessMonkey
 * at.fhv.sportsclub.controller
 * TeamController
 * 07.11.2018 sge
 */
@Repository
public interface TeamRepository extends CommonRepository<TeamEntity, String> {
}
