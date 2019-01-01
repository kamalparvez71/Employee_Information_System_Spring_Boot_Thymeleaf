
package org.employees.repository;

import java.util.List;
import org.employees.entity.Employee;
import org.springframework.data.jpa.repository.Query;
//without pagination i can use this//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author opalencia
 */
public interface EmployeesRepository extends PagingAndSortingRepository<Employee, Integer> {  

//    @Query("SELECT e.empNo, e.firstName, e.lastName, e.gender, e.birthDate, e.hireDate FROM Employee e WHERE e.lastName LIKE LOWER(CONCAT('%', :lastName, '%')) ORDER BY e.empNo")
//    public List<Employee> findByLastName(@Param("lastName") String lastName);
    
    @Query(value = "select emp_no, birth_date, first_name, last_name, gender, hire_date,photo from employees where last_name  LIKE LOWER(CONCAT('%',:lastName,'%'))", nativeQuery = true)
    public List<Employee> findByLastName(@Param("lastName") String lastName);

}
