package org.employees.repository;

import org.employees.entity.DeptManager;
import org.employees.entity.DeptManagerPK;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author opalencia
 */
public interface DeptManagerRepository extends CrudRepository<DeptManager, DeptManagerPK>{
    
}
