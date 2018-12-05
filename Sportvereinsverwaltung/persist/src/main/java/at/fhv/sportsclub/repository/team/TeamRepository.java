package at.fhv.sportsclub.repository.team;

import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.entity.team.TeamEntity;
import at.fhv.sportsclub.repository.CommonRepository;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * businessMonkey
 * at.fhv.sportsclub.controller
 * TeamController
 * 07.11.2018 sge
 */
@Repository
@CacheConfig(cacheNames = {"teams"})
public interface TeamRepository extends CommonRepository<TeamEntity, String>, CustomTeamRepository{
    List<TeamEntity> getAllByLeagueEquals(ObjectId league);
    List<TeamEntity> getAllByTrainersIsContaining(List<PersonEntity> trainers);
    @Cacheable(sync = true)
    List<TeamEntity> findAll();
}
