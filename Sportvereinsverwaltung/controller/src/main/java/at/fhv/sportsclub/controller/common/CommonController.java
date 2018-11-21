package at.fhv.sportsclub.controller.common;

import at.fhv.sportsclub.entity.CommonEntity;
import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.repository.CommonRepository;
import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessResourceFailureException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

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

public abstract class CommonController<DTO extends IDTO, E extends CommonEntity, R extends CommonRepository<E, String>>
        implements Controller<DTO, ResponseMessageDTO> {

    private final Class<DTO> dtoClass;
    private final Class<E> entityClass;

    private static final String defaultMappingPostFixLight = "MappingLight";
    private static final String defaultMappingPostFixFull = "MappingFull";

    @Autowired
    @Qualifier("generalMapper") private DozerBeanMapper mapper;

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
            E updatedEntity = this.repository.saveOrUpdate(
                    this.map(dto, this.entityClass, this.getMappingIdByConvention(defaultMappingPostFixFull))
            );
            responseMessageDTO.setContextId(updatedEntity.getId());
        } catch (DataAccessResourceFailureException e) {
            e.printStackTrace();
            return createErrorMessage("Failed to access the data source");
        } catch (MappingException e){
            return createErrorMessage("Internal server error");
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
            String mappingId = this.getMappingIdByConvention(defaultMappingPostFixLight);
            if(mappingId.isEmpty()){
                return mapAnyCollection(entityList, this.dtoClass);
            }
            List<DTO> dtos = mapAnyCollection(entityList, this.dtoClass, mappingId);
            return dtos;
        } catch (DataAccessResourceFailureException e) {
            e.printStackTrace();
            return new ArrayList<DTO>(){{
                add(rejectRequest("Failed to access the data source"));
            }};
        } catch (MappingException e){
            e.printStackTrace();
            return new ArrayList<DTO>(){{
                add(rejectRequest("Internal server error"));
            }};
        }
    }

    /**
     * Get details of an Entity by the given id
     * @param id ID to lookup
     * @param full whether to use light or full mapping
     * @return DTO for given ID with details
     */
    @Override
    public DTO getDetails(String id, boolean full) {
        E entityById;
        try {
            Optional<E> entityOptional = repository.findById(id);
            if(!entityOptional.isPresent()) {
                return rejectRequest("Nothing found for the given ID");
            }
            entityById = entityOptional.get();
        } catch (DataAccessResourceFailureException e){
            e.printStackTrace();
            return rejectRequest("Error: failed to access the data source");
        }
        try {
            String mapId = this.getMappingIdByConvention(full ? defaultMappingPostFixFull : defaultMappingPostFixLight);
            if(mapId.isEmpty()){
                return this.map(entityById, this.dtoClass);
            }
            return this.map(entityById, this.dtoClass, mapId);
        } catch (MappingException e) {
            e.printStackTrace();
            return rejectRequest("Internal server error");
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

    private String getMappingIdByConvention(String postFix){
        return dtoClass.getSimpleName() + postFix;
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
        Util methods
     */
    protected DTO rejectRequest(String infoMessage){
        DTO dto;
        try {
            dto = dtoClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        dto.setResponse(createErrorMessage(infoMessage));
        return dto;
    }

    protected ResponseMessageDTO createErrorMessage(String infoMessage){
        ResponseMessageDTO response = new ResponseMessageDTO(new LinkedList<>(), false);
        response.setInfoMessage(infoMessage);
        response.setSuccess(false);
        return response;
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