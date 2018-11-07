package at.fhv.sportsclub.controller.common;

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
    Common Controller implementation to reduce controller code for methods
    that are required in every controller (e.g. saveOrUpdate, getAll and so on).

    The following generics are used:
        DTO: destination DTO class. Refers to a POJO that is returned to the consumer
        E: Entity to use for repository communication. Usually refers to the domain/entity class
        R: Repository type, that is used for persistence communication

 */
public abstract class CommonController<DTO, E, R extends CommonRepository<E, String>>
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

    protected abstract String getId(E entity);

    @Override
    public ResponseMessageDTO saveOrUpdate(DTO dto) {
        ResponseMessageDTO responseMessageDTO = validateDto(dto);
        if (!responseMessageDTO.isValidated()) {
            return responseMessageDTO;
        }
        try {
            E updatedEntity = this.repository.saveOrUpdate(this.map(dto, this.entityClass));
            responseMessageDTO.setContextId(getId(updatedEntity));
        } catch (Exception e) {      // TODO handle right exception
            responseMessageDTO.setInfoMessage(e.getMessage());
            return responseMessageDTO;
        }
        responseMessageDTO.setSuccess(true);
        return responseMessageDTO;
    }

    @Override
    public List<DTO> getAll() {
        try {
            List<E> entityList = repository.findAll();
            if(this.getMappingIdByConvention().isEmpty()){
                return mapAnyCollection(entityList, this.dtoClass);
            }
            return mapAnyCollection(entityList, this.dtoClass); // TODO: , this.getMappingIdByConvention()
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

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

    private boolean checkMapId(String mapId){
        try {
            this.mapper.map(new PersonDTO(), PersonEntity.class, mapId);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    protected <S, D> D map(S source, Class<D> dest){
        return mapper.map(source, dest);
    }

    protected <S, D> D map(S source, Class<D> dest, String mapId){
        return mapper.map(source, dest, mapId);
    }

    private interface InternalMapper<S, D> {
        D map(S sourceElement);
    }

    /*
        Generic helper function to map collections from Source (S) to Destination (D).
        Make sure that the destination class offers an empty constructor.
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