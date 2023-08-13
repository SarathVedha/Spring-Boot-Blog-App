package com.vedha.blog.service;

import com.vedha.blog.dto.CommentDto;

import java.util.Set;

public interface CommentService {

    CommentDto createComment(Long postId, CommentDto commentDto);

    Set<CommentDto> getCommentsByPostId(Long postId);

    CommentDto getCommentByPostId(Long postId, Long commentId);

    CommentDto updateCommentBYPostId(Long postId, Long commentId, CommentDto commentDto);

    String deleteCommentByPostId(Long postId, Long commentId);
}
