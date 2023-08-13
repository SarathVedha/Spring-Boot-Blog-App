package com.vedha.blog.service.impl;

import com.vedha.blog.dto.PostDto;
import com.vedha.blog.dto.ResponseDto;
import com.vedha.blog.entity.CategoryEntity;
import com.vedha.blog.entity.PostEntity;
import com.vedha.blog.exception.ResourceNotFoundException;
import com.vedha.blog.repository.CategoryRepository;
import com.vedha.blog.repository.PostRepository;
import com.vedha.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {

        CategoryEntity category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", postDto.getCategoryId().toString()));

        PostEntity postEntity = modelMapper.map(postDto, PostEntity.class);
        postEntity.setCategoryEntity(category);
        PostEntity saved = postRepository.save(postEntity);

        return modelMapper.map(saved, PostDto.class);
    }

    @Override
    public ResponseDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortMode) {

        Sort orders = sortMode.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, orders);

        Page<PostEntity> entityPage = postRepository.findAll(pageable);

        List<PostEntity> postEntities = entityPage.getContent();

        List<PostDto> list = postEntities.stream().map(postEntity -> modelMapper.map(postEntity, PostDto.class)).toList();

        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(list);
        responseDto.setPageNo(entityPage.getNumber());
        responseDto.setPageSize(entityPage.getSize());
        responseDto.setTotalRecords(entityPage.getTotalElements());
        responseDto.setTotalPages(entityPage.getTotalPages());

        return responseDto;
    }

    @Override
    public PostDto getPostById(Long postId) {

        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId.toString()));

        return modelMapper.map(postEntity, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {

        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId.toString()));

        CategoryEntity category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", postDto.getCategoryId().toString()));

        postEntity.setId(postId);
        postEntity.setTitle(postDto.getTitle());
        postEntity.setDescription(postDto.getDescription());
        postEntity.setContent(postDto.getContent());
        postEntity.setCategoryEntity(category);

        PostEntity saved = postRepository.save(postEntity);

        return modelMapper.map(saved, PostDto.class);
    }

    @Override
    public String deletePost(Long postId) {

        postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId.toString()));

        postRepository.deleteById(postId);

        return "Post ".concat(postId.toString()).concat(" is deleted successfully");
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {

        categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId.toString()));

        List<PostEntity> byCategoryEntityId = postRepository.findByCategoryEntity_Id(categoryId);

        return byCategoryEntityId.stream().map(postEntity -> modelMapper.map(postEntity, PostDto.class)).collect(Collectors.toList());
    }
}
