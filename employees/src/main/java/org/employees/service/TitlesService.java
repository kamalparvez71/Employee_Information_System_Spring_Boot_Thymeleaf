package org.employees.service;

import org.employees.entity.Title;
import org.employees.entity.TitlePK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author opalencia
 */
public interface TitlesService {

    Page<Title> findAllTitle(Pageable pgbl);

    Title getTitleById(TitlePK id);

    Title saveTitle(Title title);

    void deleteTitle(TitlePK id);

}
