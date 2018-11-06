package at.fhv.sportsclub.common;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.repository.CommonRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class CommonController<DTO, E, R extends CommonRepository<E, String>> implements Controller<DTO, ResponseMessageDTO> {

    @Autowired
    private DozerBeanMapper mapper;
    @Autowired
    private Validator validator;

    private R repository;

    public CommonController(R repository) {
        this.repository = repository;
    }

    @Override
    public ResponseMessageDTO saveOrUpdate(DTO dto) {
        ResponseMessageDTO responseMessageDTO = validateDto(dto);
        if (!responseMessageDTO.isValidated()) {
            return responseMessageDTO;
        }
        try {
            E updatedEntity = repository.saveOrUpdate(internalMap(dto));
            responseMessageDTO.setContextId(getId(updatedEntity));
        } catch (Exception e) {      // TODO handle right exception
            responseMessageDTO.setInfoMessage(e.getMessage());
            return responseMessageDTO;
        }
        responseMessageDTO.setSuccess(true);
        return responseMessageDTO;
    }

    protected abstract E internalMap(DTO dto);

    protected abstract String getId(E entity);

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

    protected <S, D> D map(S source, Class<D> dest){
        return mapper.map(source, dest);
    }

    /*
        Generic helper function to map collections from Source (S) to Destination (D).
        Make sure that the destination class offers an empty constructor.
     */
    protected <S, D> List<D> mapCollection(List<S> source, Class<D> destinationClass){
        final List<D> destinationCollection = new LinkedList<>();
        for (S element: source) {
            destinationCollection.add(mapper.map(element, destinationClass));
        }
        return destinationCollection;
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