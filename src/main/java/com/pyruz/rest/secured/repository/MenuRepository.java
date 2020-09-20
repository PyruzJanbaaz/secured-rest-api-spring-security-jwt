package com.pyruz.rest.secured.repository;

import com.pyruz.rest.secured.model.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findMenuById(Long id);

    List<Menu> findAllByIsActiveIsTrue();

    List<Menu> findMenusByParentIdAndIsActiveIsTrue(Long parentId);

    Page<Menu> findAll(Pageable pageRequest);

    List<Menu> findMenuByIdIn(List<Long> ids);

    @Query(value = "select distinct me.* from users u join user_access ua on (u.id = ua.user_id and ua.user_id = :userId and u.is_deleted = false and u.is_active = true) join \"access\" ac on (ac.id = ua.access_id and ac.is_deleted = false and ac.is_active = true) join access_menu am on ua.access_id = am.access_id join menu me on (me.id = am.menu_id and me.is_deleted = false and me.is_active = true)", nativeQuery = true)
    List<Menu> findUserMenu(@Param("userId") Long userId);

    @Query(value = "select distinct me.role from users u join user_access ua on (u.id = ua.user_id and ua.user_id = :userId and u.is_deleted = false and u.is_active = true) join \"access\" ac on (ac.id = ua.access_id and ac.is_deleted = false and ac.is_active = true) join access_menu am on ua.access_id = am.access_id join menu me on (me.id = am.menu_id and me.is_deleted = false and me.is_active = true)", nativeQuery = true)
    List<String> findUserRoles(@Param("userId") Long userId);
}
