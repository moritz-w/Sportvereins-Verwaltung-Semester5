package at.fhv.sportsclub.repository.dept;

import at.fhv.sportsclub.entity.dept.DepartmentEntity;
import at.fhv.sportsclub.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CommonRepository<DepartmentEntity, String> {
}
