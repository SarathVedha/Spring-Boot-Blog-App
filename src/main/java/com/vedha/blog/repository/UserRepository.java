package com.vedha.blog.repository;

import com.vedha.blog.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByName(String userName);
    Optional<UserEntity> findByEmail(String userEmail);

    Optional<UserEntity> findByEmailOrName(String userEmail, String userName);

    boolean existsByName(String userName);

    boolean existsByEmail(String userEmail);
}
