package org.employees.service;

import org.employees.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author opalencia
 */
public interface EmployeesService {

    Page<Employee> findAll(Pageable pgbl);   
    
    Employee getEmployeeById(Integer id);

    Employee saveEmployee(Employee employee);

    void deleteEmployee(Integer id);

}
