package at.fhv.sportsclub.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/*
    Common Repository interface. Those methods are shared across
    all Repositories, since the CommonRepositoryImpl is used to instantiate
    the repository base class. Most of the methods are routed to the
    SimpleMongoRepository, but others are implemented in the CommonRepository
    Implementation, which uses the mongo entity manager and entity metadata
    to perform extended but generic queries.
 */
@NoRepositoryBean
public interface CommonRepository<T, ID extends Serializable> extends Repository<T, ID> {

    Iterable<T> findAll();

    Optional<T> findById(ID id);

    boolean existsById(ID var1);

    <S extends T> List<S> findAll(Example<S> var1);

    <S extends T> List<S> findAll(Example<S> var1, Sort var2);

    <S extends T> S insert(S var1);

    <S extends T> S saveOrUpdate(S entity);

    <S extends T> List<S> saveAll(Iterable<S> var1);

}
