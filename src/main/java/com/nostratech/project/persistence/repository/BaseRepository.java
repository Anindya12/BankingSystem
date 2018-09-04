package com.nostratech.project.persistence.repository;

import com.nostratech.project.persistence.domain.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by yukibuwana on 1/24/17.
 */

@NoRepositoryBean
public interface BaseRepository<T extends Base> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {

    T findBySecureId(String secureId);
    T findById(Integer id);

    boolean exists(Integer id);
}
