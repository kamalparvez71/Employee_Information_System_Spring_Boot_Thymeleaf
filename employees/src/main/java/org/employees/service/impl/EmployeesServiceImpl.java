package org.employees.service.impl;

import org.employees.service.*;
import org.employees.entity.Employee;
import org.employees.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author opalencia
 */
@Service
public class EmployeesServiceImpl implements EmployeesService {

    private EmployeesRepository employeesRepository;

    @Autowired
    public void setEmployeesRepository(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    @Override
    public Page<Employee> findAll(Pageable pgbl) {
        return employeesRepository.findAll(pgbl);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeesRepository.findOne(id);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
//                 if (employee.getFileData() != null) {
//            byte[] photo = employee.getFileData().getBytes();
//            if (photo != null && photo.length > 0) {
//                employee.setPhoto(photo);
//            }
//        }
        return employeesRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeesRepository.delete(id);
    }

}
