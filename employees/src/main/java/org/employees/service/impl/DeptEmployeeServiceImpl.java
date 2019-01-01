package org.employees.service.impl;

import org.employees.service.*;
import org.employees.entity.DeptEmp;
import org.employees.entity.DeptEmpPK;
import org.employees.repository.DeptEmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author opalencia
 */
@Service
public class DeptEmployeeServiceImpl implements DeptEmployeeService {
    
    private DeptEmployeesRepository deptEmployeesRepository;

    @Autowired
    public void setDeptEmployeesRepository(DeptEmployeesRepository deptEmployeesRepository) {
        this.deptEmployeesRepository = deptEmployeesRepository;
    }    
    
    @Override
    public Page<DeptEmp> findAllDeptEmp(Pageable pgbl) {
        return deptEmployeesRepository.findAll(pgbl);
    }

    @Override
    public DeptEmp getDeptEmpById(DeptEmpPK id) {
        return deptEmployeesRepository.findOne(id);
    }

    @Override
    public DeptEmp saveDeptEmp(DeptEmp deptEmp) {
        return deptEmployeesRepository.save(deptEmp);
    }

    @Override
    public void deleteDeptEmp(DeptEmpPK id) {
        deptEmployeesRepository.delete(id);
    }
    
    
}
