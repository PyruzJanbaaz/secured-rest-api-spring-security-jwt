package com.pyruz.rest.secured.repository;


import com.pyruz.rest.secured.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsernameIgnoreCase(String username);

    Optional<User> findUserByUsernameIgnoreCaseAndIsActiveIsTrue(String username);

    Boolean existsUserByUsernameIgnoreCase(String username);

    List<User> findAllByIsActiveIsTrue();

    Page<User> findAll(Pageable pageRequest);
}