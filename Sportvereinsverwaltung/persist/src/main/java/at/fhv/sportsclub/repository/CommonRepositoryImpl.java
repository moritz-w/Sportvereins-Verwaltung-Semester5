package at.fhv.sportsclub.repository;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;
import java.util.Optional;

public class CommonRepositoryImpl<T, ID extends Serializable>
        extends SimpleMongoRepository<T, ID> implements CommonRepository<T,ID> {

    private MongoEntityInformation<T, ID> entityMetaData;
    private MongoOperations mongoOperations;

    public CommonRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoOperations = mongoOperations;
        this.entityMetaData = metadata;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(
                mongoOperations.findById(id, entityMetaData.getJavaType())
        );
    }

    @Override
    public <S extends T> S saveOrUpdate(S entity) {
        mongoOperations.save(entity);
        return entity;
    }
}
