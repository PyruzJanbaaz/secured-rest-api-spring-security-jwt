package com.pyruz.rest.secured.repository;


import com.pyruz.rest.secured.model.entity.Access;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessRepository extends JpaRepository<Access,Long> {

    Boolean existsAccessByTitleIgnoreCase(String title);
    Page<Access> findAll(Pageable pageable);
    List<Access> findAccessByIdIn(List<Long> ids);
    Access findAccessByTitle(String title);

}
