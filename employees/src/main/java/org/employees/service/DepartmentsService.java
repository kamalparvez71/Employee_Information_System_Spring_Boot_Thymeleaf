package org.employees.service;

import org.employees.entity.Department;

/**
 *
 * @author opalencia
 */
public interface DepartmentsService {

    Iterable<Department> listAllDepartments();

    Department getDepartmentById(String id);

    Department saveDepartment(Department department);

    void deleteDepartment(String id);

}
