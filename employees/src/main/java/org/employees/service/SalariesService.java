package org.employees.service;

import org.employees.entity.Salarie;
import org.employees.entity.SalariePK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author opalencia
 */
public interface SalariesService {

    Page<Salarie> findAllSalarie(Pageable pgbl);

    Salarie getSalarieById(SalariePK id);

    Salarie saveSalarie(Salarie salarie);

    void deleteSalarie(SalariePK id);

}
