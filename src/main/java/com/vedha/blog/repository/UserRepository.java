package com.vedha.blog.repository;

import com.vedha.blog.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByName(String userName);

    Optional<UserEntity> findByEmail(String userEmail);

    Optional<UserEntity> findByEmailOrName(String userEmail, String userName);

    boolean existsByName(String userName);

    boolean existsByEmail(String userEmail);

    // JPQL
    @Query("SELECT u FROM UserEntity u WHERE u.name LIKE CONCAT( '%', :name, '%')")
    Optional<UserEntity> getUserByName(String name);

    // Native Query
    @Query(value = "SELECT * FROM users u WHERE u.email LIKE CONCAT('%', :email, '%')", nativeQuery = true)
    Optional<UserEntity> getUserByEmail(String email);
}
