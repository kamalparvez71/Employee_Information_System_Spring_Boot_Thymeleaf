package org.employees.repository;

import org.employees.entity.Title;
import org.employees.entity.TitlePK;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author opalencia
 */
public interface TitlesRepository extends PagingAndSortingRepository<Title, TitlePK> {
    
}
