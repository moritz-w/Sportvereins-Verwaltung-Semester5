package at.fhv.sportsclub.repository.tournament;

import at.fhv.sportsclub.entity.CommonEntity;
import at.fhv.sportsclub.entity.team.TeamEntity;
import at.fhv.sportsclub.entity.tournament.EncounterEntity;
import at.fhv.sportsclub.entity.tournament.ParticipantEntity;
import at.fhv.sportsclub.entity.tournament.TournamentEntity;
import com.mongodb.client.result.UpdateResult;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class CustomTournamentRepositoryImpl implements CustomTournamentRepository {

    private static final Logger logger = Logger.getRootLogger();
    private MongoOperations mongoOperations;

    @Autowired
    public CustomTournamentRepositoryImpl(MongoOperations mongoOperations){
        this.mongoOperations = mongoOperations;
    }


    //region Mongo Query
    /**
     *
     * Adds elements to the teams array if not already exists by exact match:
     * db.Tournament.updateOne(
     *      {_id: ObjectId("id")},
     *      { "$addToSet": { "teams": { "$each": [ {}, {} ] } }  }
     * )
     *
     * Overwrites the complete array with the given elements:
     * db.Tournament.updateOne(
     *      {"_id" : ObjectId("5bf4b57197710c31da9f32b0")},
     *      {"$set": { "teams": [
     *           {"_id": ObjectId("5bf4b57197710c31da9f32a8"), "team": "test", "participants": []} ]}
     *        }
     * )
     *
     * Updates the first matching array element matching the query:
     * db.Tournament.updateOne(
     *  {"_id": ObjectId("5bf4b57197710c31da9f32b0"), "teams": { "$elemMatch": { "_id" : ObjectId("5bf4b57197710c31da9f32a8") } } },
     *  { "$set": { "teams.$": { "_id" : ObjectId("5bf4b57197710c31da9f32a8"), "team" : "update me", "participants" : [ ] } } }
     * )
     *
     * Updates all array elements matching the query:
     * db.Tournament.updateOne(
     *  {"_id": ObjectId("5bf4b57197710c31da9f32b0"), "teams": { "$all": [ { "$elemMatch": { "_id" : ObjectId("5bf4b57197710c31da9f32a8") } } ]} },
     *  { "$set": { "teams.$[]": [{ "_id" : ObjectId("5bf4b57197710c31da9f32a8"), "team" : "ssio", "participants" : [ ] }] } }
     * )
     *
     * So apparently there is no possibility to 'upsert' embedded array data, so the application code needs to handle it.
     * Other option for upsert would be to first remove existing elements from the array by their ID and then add them
     * again to do the update. Any not existing value would be inserted, when the using addToSet or push operator.
     *
     */
    //endregion
    @Override
    public void addTeamToTournament(String tournamentId, List<ParticipantEntity> participantEntities) {
        for (ParticipantEntity participantEntity : participantEntities) {
            if(participantEntity.getId() == null || participantEntity.getId().isEmpty()){
                // add non existing Participant to teams array
                participantEntity.setId(new ObjectId().toHexString());
                pushToArray(tournamentId, "teams", participantEntity);
            } else {
                // update existing Participant
                updateArrayElement(tournamentId, "teams", participantEntity);
            }
        }
    }

    @Override
    public void removeTeamFromTournament(String tournamentId, List<ParticipantEntity> participantEntities){
        for (ParticipantEntity participantEntity : participantEntities) {
            if(participantEntity.getId() != null && !participantEntity.getId().isEmpty()) {
                removeArrayElement(tournamentId, "teams", participantEntity);
            }
        }
    }

    @Override
    public void addEncounterToTournament(String tournamentId, List<EncounterEntity> encounters){
        for (EncounterEntity encounter : encounters) {
            if(encounter.getId() == null || encounter.getId().isEmpty()){
                encounter.setId(new ObjectId().toHexString());
                pushToArray(tournamentId, "encounters", encounter);
            } else {
                updateArrayElement(tournamentId, "encounters", encounter);
            }
        }
    }

    @Override
    public void removeEncounterFromTournament(String tournamentId, List<EncounterEntity> encounterEntities){
        for (EncounterEntity encounterEntity : encounterEntities) {
            if(encounterEntity.getId() != null && !encounterEntity.getId().isEmpty()) {
                removeArrayElement(tournamentId, "encounters", encounterEntity);
            }
        }
    }

    @Override
    public List<TournamentEntity> getTournamentByTeamId(List<ObjectId> teamdIds){
        Query q = new Query();
        /* db.Tournament.findOne({"teams.team": { "$in": [ ObjectId("id") ]}}) */
        q.addCriteria(
          where("teams.team").in(teamdIds)
        );
        return mongoOperations.find(q, TournamentEntity.class);
    }

    private <T> void pushToArray(String documentId, String arrayName, T entity) {
        Query q = new Query();
        Update up = new Update();
        q.addCriteria(where("_id").is(documentId));
        up.addToSet(arrayName, entity);
        UpdateResult updateResult = mongoOperations.updateFirst(q, up, TournamentEntity.class);
        logger.info("Updating " + arrayName + " with " + entity.toString() + ": " + updateResult.toString());
    }

    private <T extends CommonEntity> void updateArrayElement(String documentId, String arrayName, T embeddedEntity) {
        Query q = new Query();
        Update up = new Update();
        q.addCriteria(where("_id").is(documentId)).addCriteria(where(arrayName).elemMatch(where("_id").is(embeddedEntity.getId())));
        up.set(arrayName + ".$", embeddedEntity);
        UpdateResult updateResult = mongoOperations.updateFirst(q, up, TournamentEntity.class);
        logger.info("Updating " + arrayName + " with " + embeddedEntity.toString() + ": " + updateResult.toString());
    }

    //region Mongo Query
    /**
        Removes all elements from the array that match the condition in pull operator.

        db.Tournament.updateOne(
            {"_id": ObjectId("5bf4b57197710c31da9f335b")},
            {
                "$pull": {"teams": {  "_id" : ObjectId("5bf4b57197710c31da9f3353") }}
             }
        )
     */
    //endregion
    private <T extends CommonEntity> void removeArrayElement(String documentId, String arrayName, T embeddedEntity){
        Query q = new Query();
        Update up = new Update();
        q.addCriteria(where("_id").is(new ObjectId(documentId)));
        up.pull(arrayName, new Document("_id", new ObjectId(embeddedEntity.getId())));
        UpdateResult updateResult = mongoOperations.updateFirst(q, up, TournamentEntity.class);
        logger.info("Updating " + arrayName + " with " + embeddedEntity.toString() + ": " + updateResult.toString());
    }
}
