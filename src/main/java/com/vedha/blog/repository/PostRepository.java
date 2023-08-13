package com.vedha.blog.repository;

import com.vedha.blog.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findByCategoryEntity_Id(Long categoryId);
}
