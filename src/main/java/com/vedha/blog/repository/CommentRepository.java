package com.vedha.blog.repository;

import com.vedha.blog.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Set<CommentEntity> findByPostEntity_Id(long blogPostId);
}
