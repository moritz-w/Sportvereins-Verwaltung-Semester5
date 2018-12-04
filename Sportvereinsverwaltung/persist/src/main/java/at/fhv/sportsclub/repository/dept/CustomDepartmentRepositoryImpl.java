package at.fhv.sportsclub.repository.dept;

import at.fhv.sportsclub.entity.dept.DepartmentEntity;
import at.fhv.sportsclub.entity.dept.LeagueEntity;
import at.fhv.sportsclub.entity.dept.SportEntity;
import at.fhv.sportsclub.exception.DataAccessException;
import at.fhv.sportsclub.exception.InvalidInputDataException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Repository;

import java.util.List;

// Criteria static import to improve readability
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;          // project, match
import static org.springframework.data.mongodb.core.aggregation.ArrayOperators.Filter.*;       // filter
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators.Eq;
import static org.springframework.data.mongodb.core.aggregation.VariableOperators.mapItemsOf;    // map

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
            throw new DataAccessException("No sports could be obtained for the given id '" + id +"'");
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
     * db.Department.find({"sports.leagues._id": ObjectId("id")}, {"sports.$": 1})
     * Alternative:
     * db.Department.findOne(
     * {
     *      "sports.leagues._id": ObjectId("5bf3462197710c31da9f30df")
     * },
     * {
     *          "sports": {$elemMatch: {"leagues._id": ObjectId("5bf3462197710c31da9f30df")}}
     * })
     */
    //endregion
    @Override
    public SportEntity getSportByLeagueId(String id) throws InvalidInputDataException, DataAccessException {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e){
            throw new InvalidInputDataException("Invalid ID type for given string '" + id + "', a hex string is required");
        }

        Query q = new Query();
        q.addCriteria(
                where("sports.leagues._id").is(objectId)
        ).fields().elemMatch("sports", where("leagues._id").is(objectId));

        DepartmentEntity departmentEntity = mongoOperations.findOne(q, DepartmentEntity.class);

        if(departmentEntity.getSports() == null || departmentEntity.getSports().isEmpty()){
            throw new DataAccessException("No sports could be obtained for the given league id '" + id +"'");
        }
        return departmentEntity.getSports().get(0);
    }

    //region Mongo Query
    /* Aggregate Query: db.Collection.aggregate([])
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
    public LeagueEntity getLeagueById(String id) throws InvalidInputDataException, DataAccessException {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e){
            throw new InvalidInputDataException("Invalid ID type for given string '" + id + "', a hex string is required");
        }
        Aggregation aggregation = newAggregation(
                match(where("sports.leagues._id").is(objectId)),        // find departments of matching league ID
                new ProjectionOperation(
                        Fields.fields("sports")                         // only project field sports of the department
                ).and(
                        aggregationOperationContext -> new Document(
                                "$filter", new Document(                // filter only sports, where size of matching leagues > 0
                                // for all sports apply the given filter on leagues
                                        "input", mapItemsOf("sports").as("sport").andApply(
                                                 innerOperationContext -> new Document(
                                                    "leagues", new Document(
                                                        // filter from league array, only those Documents matching the condition
                                                        "$filter", new Document(
                                                            "input", "$$sport.leagues"
                                                        ).append(
                                                            "as", "league"
                                                        ).append(
                                                            "cond", Eq.valueOf("$$league._id").equalToValue(
                                                                    objectId
                                                            ).toDocument(innerOperationContext)
                                                        )
                                                    )
                                                 )
                                            ).toDocument(aggregationOperationContext)
                                ).append(
                                        "as", "sport"
                                ).append(
                                        "cond", ComparisonOperators.Gt.valueOf(
                                            ArrayOperators.Size.lengthOfArray("$$sport.leagues")
                                        ).greaterThanValue(0).toDocument(aggregationOperationContext)
                                )
                        )
                ).as("sports")
        );
        AggregationResults<DepartmentEntity> results =
                mongoOperations.aggregate(aggregation, "Department", DepartmentEntity.class);
        List<DepartmentEntity> entities = results.getMappedResults();
        if(entities.isEmpty()){
            throw new DataAccessException("No leagues could be obtained for the given id '" + id +"'");
        }
        StandardEvaluationContext context = new StandardEvaluationContext(entities);
        LeagueEntity leagueEntity;
        try {
            leagueEntity = parser.parseExpression(
                    "#this[0].sports[0].leagues[0]").getValue(context, LeagueEntity.class
            );
        } catch (SpelEvaluationException e){
            throw new DataAccessException("Failed to extract LeagueEntity from root object");
        }
        return leagueEntity;
    }
}
