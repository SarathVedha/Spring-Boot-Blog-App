package com.vedha.blog.repository;

import com.vedha.blog.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String userName);

    boolean existsByName(String roleName);
}
