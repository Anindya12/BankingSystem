package com.nostratech.project.persistence.repository;

import com.nostratech.project.persistence.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value="SELECT t FROM User t WHERE t.username = :username")
    User findByUsername2(@Param("username") String username);

    User findByPassword(String password);

    @Query(value="SELECT t FROM User t WHERE lower (t.fullName) LIKE lower ('%'||:fullName||'%') ")
    Page<User> findByFullName(@Param("fullName") String fullName, Pageable pageable);

    @Query(value="SELECT t FROM User t WHERE lower (t.username) LIKE lower ('%'||:username||'%') ")
    Page<User> findByUsername(@Param("username") String username, Pageable pageable);

    @Query(value="SELECT t FROM User t WHERE t.secureId = :accountId")
    Page<User> findByAccountId(@Param("accountId") String accountId, Pageable pageable);
}
