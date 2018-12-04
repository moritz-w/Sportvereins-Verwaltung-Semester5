package at.fhv.sportsclub.entity;

/*
    Interface that defines common methods, that are shared
    across all entities. Currently only the getId getter is required,
    which is used to obtain the database ID in the saveOrUpdate method.
    The database Id is returned in the ResponseDTO as ContextDTO.
 */
public interface CommonEntity {
    String getId();
}
