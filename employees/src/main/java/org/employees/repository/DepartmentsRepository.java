package org.employees.repository;

import org.employees.entity.Department;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author opalencia
 */
public interface DepartmentsRepository extends CrudRepository<Department, String> {
    
}
