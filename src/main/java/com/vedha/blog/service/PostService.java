package com.vedha.blog.service;

import com.vedha.blog.dto.PostDto;
import com.vedha.blog.dto.ResponseDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    ResponseDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortMode);

    PostDto getPostById(Long postId);

    PostDto updatePost(PostDto postDto, Long postId);

    String deletePost(Long postId);

    List<PostDto> getPostsByCategory(Long categoryId);
}
