package org.employees.repository;

import org.employees.entity.Salarie;
import org.employees.entity.SalariePK;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author opalencia
 */
public interface SalariesRepository extends PagingAndSortingRepository<Salarie, SalariePK> {
    
}
