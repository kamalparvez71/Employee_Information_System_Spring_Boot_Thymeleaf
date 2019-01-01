package org.employees.service.impl;

import org.employees.service.*;
import org.employees.entity.Department;
import org.employees.repository.DepartmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author opalencia
 */
@Service
public class DepartmentsServiceImpl  implements DepartmentsService {
    
    private DepartmentsRepository departmentsRepository;

    @Autowired
    public void setDepartmentsRepository(DepartmentsRepository departmentsRepository) {
        this.departmentsRepository = departmentsRepository;
    }    
    
    @Override
    public Iterable<Department> listAllDepartments() {
        return departmentsRepository.findAll();
    }

    @Override
    public Department getDepartmentById(String id) {
        return departmentsRepository.findOne(id);
    }

    @Override
    public Department saveDepartment(Department department) {
        return departmentsRepository.save(department);
    }

    @Override
    public void deleteDepartment(String id) {
        departmentsRepository.delete(id);
    }
    
}
