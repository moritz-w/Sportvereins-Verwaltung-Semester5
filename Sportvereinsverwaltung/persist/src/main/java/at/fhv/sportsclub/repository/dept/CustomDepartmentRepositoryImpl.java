package at.fhv.sportsclub.repository.dept;

import at.fhv.sportsclub.entity.dept.DepartmentEntity;
import at.fhv.sportsclub.entity.dept.LeagueEntity;
import at.fhv.sportsclub.entity.dept.SportEntity;
import at.fhv.sportsclub.exception.DataAccessException;
import at.fhv.sportsclub.exception.InvalidInputDataException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Repository;

import java.util.List;

// Criteria static import to improve readability
import static org.springframework.data.mongodb.core.query.Criteria.where;

/*
      Created: 12.11.2018
      Author: Moritz W.
      Co-Authors:
*/
@Repository
public class CustomDepartmentRepositoryImpl implements CustomDepartmentRepository {

    private final SpelExpressionParser parser;
    private MongoOperations mongoOperations;

    @Autowired
    public CustomDepartmentRepositoryImpl(MongoOperations mongoOperations){
        this.mongoOperations = mongoOperations;
        this.parser = new SpelExpressionParser();
    }

    //region Mongo Query
    /*
     * Mongo Query: db.Department.find({"sports._id": ObjectId("id")}, {"sports.$": 1})
     * Alternative:
     * db.Department.find(
     *      { "sports._id": ObjectId("id") },
     *      {
     *          "sports" :
     *              { $elemMatch: { "_id": ObjectId("id") } }
     *       }
     * )
     */
    //endregion
    /**
     * Custom method to extract embedded Sport entities from the department
     * @param id Sports ID to lookup
     * @return Corresponding Sport Entity, matching to the given ID.
     */
    @Override
    public SportEntity getSportById(String id) throws InvalidInputDataException, DataAccessException {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e){
            throw new InvalidInputDataException("Invalid ID type for given string '" + id + "', a hex string is required");
        }

        Query q = new Query();
        q.addCriteria(
                where("sports._id").is(objectId)
        ).fields().elemMatch("sports", where("_id").is(objectId));

        List<DepartmentEntity> departmentEntities = mongoOperations.find(q, DepartmentEntity.class);
        if(departmentEntities.isEmpty()){
            throw new DataAccessException("No results could be obtained for the given id '" + id +"'");
        }

        StandardEvaluationContext context = new StandardEvaluationContext(departmentEntities);
        SportEntity sportEntity;
        try {
             sportEntity = parser.parseExpression(
                    "#this[0].sports[0]").getValue(context, SportEntity.class
            );
        } catch (SpelEvaluationException e){
            throw new DataAccessException("Failed to extract SportEntities from root object");
        }
        return sportEntity;
    }

    //region Mongo Query
    /*
     * Mongo query for resolving leagues by id with different filters:
     * [
     *  {
     *   $match: { "sports.leagues._id": ObjectId("5be617a79772fb0c5d2fadad") }
     *  },
     *  {
     *   $project: {
     * 	   "sports": {
     * 		$filter: {
     * 		 "input": {
     * 		  $map : {
     * 		   "input": "$sports",
     * 		   "as": "sport",
     * 		   "in" : {
     * 			"leagues": {
     * 			 $filter: {
     * 			  "input": "$$sport.leagues",
     * 			  "as": "league",
     * 			  "cond": { $eq: ["$$league._id", ObjectId("5be617a79772fb0c5d2fadad")] }
     *           }}}} },
     * 	    "as": "sport",
     * 	    "cond": {
     * 	    $gt: [ {$size: "$$sport.leagues"}, 0 ]
     *      }
     *     }}}}
     * ]
     */
    //endregion
    /**
     * Custom method, to extract embedded League Entities from the department by their ID
     * @param id League ID to look up
     * @return
     */
    @Override
    public LeagueEntity getLeagueById(String id) {
        return null;
    }
}
