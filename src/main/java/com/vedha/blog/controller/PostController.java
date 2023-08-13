package com.vedha.blog.controller;

import com.vedha.blog.dto.PostDto;
import com.vedha.blog.dto.ResponseDto;
import com.vedha.blog.service.PostService;
import com.vedha.blog.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/blog")
@AllArgsConstructor
@Tag(name = "CURD REST APIs For Post")
public class PostController {

    private final PostService postService;

    // http://localhost:8080/api/blog/post
    @Operation(summary = "Create Posts", description = "Creates and Save Post In DataBase")
    @ApiResponse(responseCode = "201", description = "Http Status 201 Created")
    @SecurityRequirement(name = "Jwt Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(
            value = {"/v1/post"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {

        PostDto post = postService.createPost(postDto);

        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/blog/posts?pageNo=0&pageSize=2
    @Operation(summary = "Get All Posts", description = "Get All Created Post In DataBase")
    @ApiResponse(responseCode = "200", description = "Http status 200 Ok")
    @GetMapping(
            value = {"/v1/posts"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<ResponseDto> getAllPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                                     @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                     @RequestParam(value = "sortMode", defaultValue = AppConstants.DEFAULT_SORT_BY_MODE, required = false) String sortMode) {

        ResponseDto allPosts = postService.getAllPosts(pageNo, pageSize, sortBy, sortMode);

        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    // http://localhost:8080/api/blog/getPost/1
    @Operation(summary = "Get Post By PostId Version-1", description = "Get Post By PostId In DataBase")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    @GetMapping(
            value = {"/v1/getPost/{postId}"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable("postId") Long postId) {

        PostDto postById = postService.getPostById(postId);

        return new ResponseEntity<>(postById, HttpStatus.OK);
    }

    /**
     * Versioning API by URL Level By Adding V1,V2,...
     */
    // http://localhost:8080/api/blog/getPost/1
    @Operation(summary = "Get Post By PostId Version-2", description = "Get Post By PostId In DataBase")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    @GetMapping(
            value = {"/v2/getPost/{postId}"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<PostDto> getPostByIdV2(@PathVariable("postId") Long postId) {

        PostDto postById = postService.getPostById(postId);

        return new ResponseEntity<>(postById, HttpStatus.OK);
    }

    // http://localhost:8080/api/blog/updatePost/1
    @Operation(summary = "Update Post By PostId", description = "Update Post By PostId In DataBase")
    @ApiResponse(responseCode = "201", description = "Http Status 201 Created")
    @SecurityRequirement(name = "Jwt Authentication")
    @PreAuthorize("hasRole('USER')")
    @PutMapping(
            value = {"/v1/updatePost/{postId}"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<PostDto> updatePost(@PathVariable("postId") Long postId, @Valid @RequestBody PostDto postDto) {

        PostDto updatedPost = postService.updatePost(postDto, postId);

        return new ResponseEntity<>(updatedPost, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/blog/deletePost/1
    @Operation(summary = "Delete Post By Id", description = "Delete Post By PostId In DataBase")
    @ApiResponse(responseCode = "202", description = "Http Status 202 Accepted")
    @SecurityRequirement(name = "Jwt Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(
            value = {"/v1/deletePost/{postId}"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.TEXT_PLAIN_VALUE}
    )
    public ResponseEntity<String> deletePost(@PathVariable("postId") Long postId) {

        String deletedPost = postService.deletePost(postId);

        return new ResponseEntity<>(deletedPost, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Get Posts By CategoryId", description = "Get All Posts By CategoryId")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    @GetMapping(value = { "/v1/getPost/category/{categoryId}" }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable("categoryId") Long categoryId) {

        List<PostDto> postsByCategory = postService.getPostsByCategory(categoryId);

        return ResponseEntity.ok(postsByCategory);
    }
}
