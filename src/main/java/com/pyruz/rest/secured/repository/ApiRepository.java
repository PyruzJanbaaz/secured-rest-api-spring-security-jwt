package com.pyruz.rest.secured.repository;

import com.pyruz.rest.secured.model.entity.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApiRepository extends JpaRepository<Api, Long> {


    Page<Api> findAll(Pageable pageRequest);

    List<Api> findApiByIdIn(List<Long> ids);

    @Query(value = "select distinct me.role from users u " +
            "join user_access ua on (u.id = ua.user_id and ua.user_id = :userId and u.is_deleted = false and u.is_active = true) " +
            "join \"access\" ac on (ac.id = ua.access_id and ac.is_deleted = false and ac.is_active = true) " +
            "join access_api aa on ua.access_id = aa.api_id " +
            "join api ap on (ap.id = aa.api_id and ap.is_deleted = false and ap.is_active = true)", nativeQuery = true)
    List<String> findUserRoles(@Param("userId") Long userId);
}
