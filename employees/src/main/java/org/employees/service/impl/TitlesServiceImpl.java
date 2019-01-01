package org.employees.service.impl;

import org.employees.service.*;
import org.employees.entity.Title;
import org.employees.entity.TitlePK;
import org.employees.repository.TitlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author opalencia
 */
@Service
public class TitlesServiceImpl implements TitlesService {
    
    private TitlesRepository titlesRepository;
    
    @Autowired
    public void setTitlesRepository(TitlesRepository titlesRepository) {
        this.titlesRepository = titlesRepository;
    }        

    @Override
    public Page<Title> findAllTitle(Pageable pgbl) {
        return titlesRepository.findAll(pgbl);
    }

    @Override
    public Title getTitleById(TitlePK id) {
        return titlesRepository.findOne(id);
    }

    @Override
    public Title saveTitle(Title title) {
        return titlesRepository.save(title);
    }

    @Override
    public void deleteTitle(TitlePK id) {
        titlesRepository.delete(id);
    }
    
}
