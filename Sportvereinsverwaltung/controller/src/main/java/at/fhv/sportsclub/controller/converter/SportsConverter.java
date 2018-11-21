package at.fhv.sportsclub.controller.converter;

import at.fhv.sportsclub.controller.resolver.SportsResolver;
import at.fhv.sportsclub.model.dept.SportDTO;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.dozer.CustomConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
      Created: 15.11.2018
      Author: Moritz W.
      Co-Authors: 
*/

/**
 * Unfortunately this is the only working solution with Dozer's custom Converters.
 * The new DozerConverter<Source, Target> API with hints for List types doesn't
 * work with Lists. This solution would use hints and a mapping for
 * those two distinct objects (e.g. ObjectId to SportDTO). Then Dozer loops automatically
 * over the source list and translates it to a new list with the help of the converter.
 * To actually set a custom converter by it's id or type, a field needs to be specified.
 * So as a field type for the mapping 'this' is used:
 *
 *     <mapping map-id="ResolveObjectIdToSportEntity" >
 *         <class-a>org.bson.types.ObjectId</class-a>
 *         <class-b>at.fhv.sportsclub.model.dept.SportDTO</class-b>
 *         <field custom-converter-id="sportsResolver">
 *             <a>this</a>
 *             <b>this</b>
 *         </field>
 *     </mapping>
 *
 * The mapping with the hint will then use the mapping given here including the converter.
 * But apparently Dozer nulls out all the values...
 *
 * So instead the old API is used. This solution is not as clean, as the one described above, but
 * there is no other way at the moment. The class below simply accepts whole lists.
 * Mapping whole lists with the new API is also not possible, since it would require a constructor,
 * that is instantiated by dozer, but since it's a bean, the constructor is invoked by the Spring container.
 * Using List wrappers would be possible, but requires additional Converters for Spring Data.
 *
 * There is also a stackoverflow question related to this problem:
 * https://stackoverflow.com/questions/53334730/dozer-nulls-return-value-of-custom-converter
 *
 */
@Component("sportsConverter")
public class SportsConverter implements CustomConverter {

    private static final Logger logger = Logger.getRootLogger();
    private final SportsResolver sportsResolver;


    @Autowired
    public SportsConverter(@Qualifier("sportsResolver") SportsResolver sportsResolver){
        this.sportsResolver = sportsResolver;
    }

    @Override
    public Object convert(Object dest, Object source, Class<?> aClass, Class<?> aClass1) {
        if (source == null) {
            return null;
        }

        if (source instanceof List && !((List) source).isEmpty()){

            if (((List) source).get(0) instanceof ObjectId){
                ArrayList<SportDTO> dtos = new ArrayList<>();
                for (ObjectId id :(List<ObjectId>)source) {
                    dtos.add(sportsResolver.resolveFromObjectId(id.toHexString()));
                }
                return dtos;
            }
            else if (((List) source).get(0) instanceof SportDTO){
                ArrayList<ObjectId> objectIds = new ArrayList<>();
                for (SportDTO dto :(List<SportDTO>)source) {
                    objectIds.add(sportsResolver.resolveFromDTO(dto));
                }
                return objectIds;
            }
        }
        return null;
    }

}

/*public class SportsConverter extends DozerConverter<ObjectId, SportDTO> {

    private static final Logger logger = Logger.getRootLogger();
    private final DepartmentRepository departmentRepository;
    private final Mapper resolverMapper;

    @Autowired
    public SportsConverter(DepartmentRepository departmentRepository, @Qualifier("resolverMapper") Mapper resolverMapper){
        super(ObjectId.class, SportDTO.class);
        this.departmentRepository = departmentRepository;
        this.resolverMapper = resolverMapper;
    }

    @Override
    public SportDTO convertTo(ObjectId objectId, SportDTO sportDTO) {
        // resolve and convert
    }

    @Override
    public ObjectId convertFrom(SportDTO SportDTO, ObjectId objectId) {
        return null;
    }
}*/
