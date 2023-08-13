package com.vedha.blog.service.impl;

import com.vedha.blog.dto.CommentDto;
import com.vedha.blog.entity.CommentEntity;
import com.vedha.blog.entity.PostEntity;
import com.vedha.blog.exception.BlogCommentNotBelongException;
import com.vedha.blog.exception.ResourceNotFoundException;
import com.vedha.blog.repository.CommentRepository;
import com.vedha.blog.repository.PostRepository;
import com.vedha.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;


    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post ID", postId.toString()));

        CommentEntity commentEntity = modelMapper.map(commentDto, CommentEntity.class);
        commentEntity.setPostEntity(postEntity);

        CommentEntity save = commentRepository.save(commentEntity);

        return modelMapper.map(save, CommentDto.class);
    }

    @Override
    public Set<CommentDto> getCommentsByPostId(Long postId) {

        postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId.toString()));

        Set<CommentEntity> byPostEntityId = commentRepository.findByPostEntity_Id(postId);

        return byPostEntityId.stream().map(commentEntity -> modelMapper.map(commentEntity, CommentDto.class)).collect(Collectors.toSet());
    }

    @Override
    public CommentDto getCommentByPostId(Long postId, Long commentId) {

        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId.toString()));

        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "Comment Id", commentId.toString()));

        if (!postEntity.getId().equals(commentEntity.getPostEntity().getId())) {

            throw new BlogCommentNotBelongException("Blog Comment Id Not Belongs To The Post : " + commentId);
        }

        return modelMapper.map(commentEntity, CommentDto.class);
    }

    @Override
    public CommentDto updateCommentBYPostId(Long postId, Long commentId, CommentDto commentDto) {

        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId.toString()));

        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "Comment Id", commentId.toString()));

        if (postEntity.getId().equals(commentEntity.getPostEntity().getId())) {

            commentDto.setId(commentEntity.getId());
            CommentEntity map = modelMapper.map(commentDto, CommentEntity.class);
            map.setPostEntity(postEntity);
            CommentEntity save = commentRepository.save(map);

            return modelMapper.map(save, CommentDto.class);

        }else {

            throw (new BlogCommentNotBelongException("Blog Comment Not Belongs to Post : " + commentId));
        }
    }

    @Override
    public String deleteCommentByPostId(Long postId, Long commentId) {

        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId.toString()));

        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "Comment Id", commentId.toString()));

        if (postEntity.getId().equals(commentEntity.getPostEntity().getId())) {

            commentRepository.deleteById(commentEntity.getId());
            return "Comment Deleted SuccessFully!";
        }else {

            throw new BlogCommentNotBelongException("Comment Id Not Belongs to the post : " + postId);
        }
    }


}
