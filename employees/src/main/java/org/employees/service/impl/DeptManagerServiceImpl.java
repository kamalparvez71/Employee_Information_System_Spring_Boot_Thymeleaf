package org.employees.service.impl;

import org.employees.service.*;
import org.employees.entity.DeptManager;
import org.employees.entity.DeptManagerPK;
import org.employees.repository.DeptManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author opalencia
 */
@Service
public class DeptManagerServiceImpl implements DeptManagerService {
    
    private DeptManagerRepository deptManagerRepository;

    @Autowired
    public void setDeptManagerRepository(DeptManagerRepository deptManagerRepository) {
        this.deptManagerRepository = deptManagerRepository;
    }    
    
    @Override
    public Iterable<DeptManager> listAllDeptManager() {
        return deptManagerRepository.findAll();
    }

    @Override
    public DeptManager getDeptManagerById(DeptManagerPK id) {
        return deptManagerRepository.findOne(id);
    }

    @Override
    public DeptManager saveDeptManager(DeptManager deptManager) {
        return deptManagerRepository.save(deptManager);
    }

    @Override
    public void deleteDeptManager(DeptManagerPK id) {
        deptManagerRepository.delete(id);
    }
    
}
