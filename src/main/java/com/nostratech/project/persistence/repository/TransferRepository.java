package com.nostratech.project.persistence.repository;

import com.nostratech.project.persistence.domain.Transfer;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    Page<Transfer> findByAccountIdSource (String accountIdSource, Pageable pageable);

    Page<Transfer> findByAccountIdDest (String accountIdDest, Pageable pageable);

    Page<Transfer> findByAccountIdSourceAndAccountIdDest (String accountIdSource, String accountIdDest,Pageable pageable);

}
