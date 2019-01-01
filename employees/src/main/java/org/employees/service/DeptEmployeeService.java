package org.employees.service;

import org.employees.entity.DeptEmp;
import org.employees.entity.DeptEmpPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author opalencia
 */
public interface DeptEmployeeService {
    
    Page<DeptEmp> findAllDeptEmp(Pageable pgbl); 
    
    DeptEmp getDeptEmpById(DeptEmpPK id);

    DeptEmp saveDeptEmp(DeptEmp deptEmp);

    void deleteDeptEmp(DeptEmpPK id);
    
}
