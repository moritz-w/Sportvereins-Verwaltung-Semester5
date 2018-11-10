package at.fhv.sportsclub.controller.common;

import at.fhv.sportsclub.entity.CommonEntity;
import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.repository.CommonRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
    Created: 05.11.2018
    Author: Moritz W.
    Co-Authors:
*/

/**
 * Common Controller implementation to reduce controller code for methods
 * that are required in every controller (e.g. saveOrUpdate, getAll and so on).
 *
 * @param <DTO> destination DTO class. Refers to a POJO that is returned to the consumer
 * @param <E> Entity to use for repository communication. Usually refers to the domain/entity class
 * @param <R> Repository type, that is used for persistence communication
 */

public abstract class CommonController<DTO, E extends CommonEntity, R extends CommonRepository<E, String>>
        implements Controller<DTO, ResponseMessageDTO> {

    private final Class<DTO> dtoClass;
    private final Class<E> entityClass;

    private static final String defaultMappingPostFixLight = "MappingLight";

    @Autowired
    private DozerBeanMapper mapper;
    @Autowired
    private Validator validator;

    private R repository;

    public CommonController(R repository, Class<DTO> dtoClass, Class<E> entityClass) {
        this.repository = repository;
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    /**
     * Save or update (if id already exists) the database for the given DTO.
     * @param dto DTO (Pojo) containing the data that should be changed or inserted
     * @return ResponseMessageDTO, which holds information, whether the validation was successful
     * and the contextId, which refers to ID of a newly created or updated Entity.
     */
    @Override
    public ResponseMessageDTO saveOrUpdate(DTO dto) {
        ResponseMessageDTO responseMessageDTO = validateDto(dto);
        if (!responseMessageDTO.isValidated()) {
            return responseMessageDTO;
        }
        try {
            E updatedEntity = this.repository.saveOrUpdate(this.map(dto, this.entityClass));
            responseMessageDTO.setContextId(updatedEntity.getId());
        } catch (Exception e) {      // TODO handle right exception
            responseMessageDTO.setInfoMessage(e.getMessage());
            return responseMessageDTO;
        }
        responseMessageDTO.setSuccess(true);
        return responseMessageDTO;
    }

    /**
     * Returns a list of light DTOs containing all database entry for the given entity class in the generic.
     * Light means, that a dozer mapping id (context based mapping) is used for mapping. The corresponding
     * XML file defining that id, contains the fields that should be mapped. By this, you can reduce the overall
     * traffic, when requesting a long list of heavy DTOs.
     * @return A list of light mapped DTOs.
     */
    @Override
    public List<DTO> getAll() {
        try {
            List<E> entityList = repository.findAll();
            if(this.getMappingIdByConvention().isEmpty()){
                return mapAnyCollection(entityList, this.dtoClass);
            }
            return mapAnyCollection(entityList, this.dtoClass, this.getMappingIdByConvention()); // TODO: , this.getMappingIdByConvention()
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Simple validation method for validating the javax.validation annotations, used in the DTOs.
     * @param dto DTO to validate
     * @return ResponseMessageDTO with validated set to true or false and a list of validationMessages (if any exist)
     */
    protected ResponseMessageDTO validateDto(DTO dto) {
        List<String> violationMessages = new LinkedList<>();
        Set<ConstraintViolation<DTO>> constraintViolations = this.validator.validate(dto);
        if(constraintViolations.isEmpty()){
            return new ResponseMessageDTO(violationMessages, true);
        }
        for (ConstraintViolation<DTO> constraintViolation : constraintViolations) {
            violationMessages.add(constraintViolation.getMessage());
        }
        return new ResponseMessageDTO(violationMessages, false);
    }

    private String getMappingIdByConvention(){
        String byDtoName = dtoClass.getSimpleName() + defaultMappingPostFixLight;
        String byEntityName = entityClass.getSimpleName() + defaultMappingPostFixLight;
        if (checkMapId(byDtoName)){
            return byDtoName;
        }
        if (checkMapId(byEntityName)) {
            return byEntityName;
        }
        return "";
    }

    // Note: this method checks if a mapping id exist. Dozer doesn't offer a method for that,
    // so that's a very basic (and ugly) solution
    private boolean checkMapId(String mapId){
        try {
            this.mapper.map(new PersonDTO(), PersonEntity.class, mapId);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Simple generic method for mapping. It might seem to be a unnecessary method,
     * since mapping could be done with the mapper directly, but the subclass extending
     * this class, should only have a subset of mapping functions available.
     * @param source Source entity
     * @param dest Destination entity class
     * @param <S> Source entity type
     * @param <D> Destination entity type
     * @return new instance of destination entity class with values mapped
     */
    protected <S, D> D map(S source, Class<D> dest){
        return mapper.map(source, dest);
    }

    protected <S, D> D map(S source, Class<D> dest, String mapId){
        return mapper.map(source, dest, mapId);
    }


    private interface InternalMapper<S, D> {
        D map(S sourceElement);
    }

    /**
     *  Generic helper function to map collections from Source (S) to Destination (D).
     *  Make sure that the destination class offers an empty constructor.
     * @param source Source list containing entities to map
     * @param mapper Mapping interface with a map method, that is executed on every entity instance of the source list
     * @param <S> Source type
     * @param <D> Destination type
     * @return List of destination entities
     */
    protected <S, D> List<D> mapAnyCollection(List<S> source, InternalMapper<S, D> mapper){
        final List<D> destinationCollection = new LinkedList<>();
        for (S element: source) {
            destinationCollection.add(mapper.map(element));
        }
        return destinationCollection;
    }

    protected <S, D> List<D> mapAnyCollection(List<S> source, Class<D> destinationClass){
        return this.mapAnyCollection(source, sourceElement -> this.map(sourceElement, destinationClass));
    }

    protected <S, D> List<D> mapAnyCollection(List<S> source, Class<D> destinationClass, String mapId){
        return this.mapAnyCollection(source, sourceElement -> this.map(sourceElement, destinationClass, mapId));
    }

    /*
        Setters for field injection
    */
    public void setMapper(DozerBeanMapper mapper) {
        this.mapper = mapper;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}