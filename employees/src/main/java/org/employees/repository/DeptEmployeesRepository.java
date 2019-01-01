package org.employees.repository;

import org.employees.entity.DeptEmp;
import org.employees.entity.DeptEmpPK;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author opalencia
 */
public interface DeptEmployeesRepository extends PagingAndSortingRepository<DeptEmp, DeptEmpPK> {
    
}
