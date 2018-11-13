package at.fhv.sportsclub.repository.person;

import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.repository.CommonRepository;
import org.springframework.stereotype.Repository;


/*
    The Person Repository interface is an actual usable interface, that
    will be used to instantiate a data store specific object, that
    contains some CRUD functionality. In most cases this would be a
    SimpleMongoRepository, that contains the CRUD functionality. But since
    not all methods should be exposed to the controller layer (e.g deleteAll)
    a CommonRepository is used, which limits the available methods to the
    required functionality. All the methods defined in the Common Repository
    interface are routed to the corresponding data store specific implementation.

    With the CustomPersonRepository, domain specific methods (or queries) can be added.
    The Impl postfix of CustomPersonRepository is detected by Spring and used to instantiate
    the Repository.

    Methods in this interface are also domain specific, but the method names are resolved
    and parsed to queries by the Spring Data module.
 */
@Repository
public interface PersonRepository extends
                                        CommonRepository<PersonEntity, String>,
                                        CustomPersonRepository
{

}
