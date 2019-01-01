package org.employees.service;

import org.employees.entity.DeptManager;
import org.employees.entity.DeptManagerPK;

/**
 *
 * @author opalencia
 */
public interface DeptManagerService {
    
    Iterable<DeptManager> listAllDeptManager();

    DeptManager getDeptManagerById(DeptManagerPK id);

    DeptManager saveDeptManager(DeptManager deptManager);

    void deleteDeptManager(DeptManagerPK id);
    
}
