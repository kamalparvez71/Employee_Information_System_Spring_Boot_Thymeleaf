package org.employees.service.impl;

import org.employees.service.*;
import org.employees.entity.Salarie;
import org.employees.entity.SalariePK;
import org.employees.repository.SalariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author opalencia
 */
@Service
public class SalariesServiceImpl implements SalariesService {
    
    private SalariesRepository salariesRepository;

    @Autowired
    public void setSalariesRepository(SalariesRepository salariesRepository) {
        this.salariesRepository = salariesRepository;
    }        

    @Override
    public Page<Salarie> findAllSalarie(Pageable pgbl) {
        return salariesRepository.findAll(pgbl);
    }

    @Override
    public Salarie getSalarieById(SalariePK id) {
        return salariesRepository.findOne(id);
    }

    @Override
    public Salarie saveSalarie(Salarie salarie) {
        return salariesRepository.save(salarie);
    }

    @Override
    public void deleteSalarie(SalariePK id) {
        salariesRepository.delete(id);
    }    
    
}
