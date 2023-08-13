package com.vedha.blog.controller;

import com.vedha.blog.dto.CommentDto;
import com.vedha.blog.service.CommentService;
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

import java.util.Set;

@RestController
@RequestMapping("/api/blog/")
@AllArgsConstructor
@Tag(name = "CURD REST APIs For Comment")
public class CommentController {

    private final CommentService commentService;

    // http://localhost:8080/api/blog/{postId}/comment
    @Operation(summary = "Crate Comment By PostId", description = "Creates Comment By PostId In DataBase")
    @ApiResponse(responseCode = "201", description = "Http Status 201 Created")
    @SecurityRequirement(name = "Jwt Authentication")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping(
            value = {"/v1/{postId}/comment"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") Long postId, @Valid @RequestBody CommentDto commentDto) {

        CommentDto comment = commentService.createComment(postId, commentDto);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/blog/{postId}/comments
    @Operation(summary = "Get All Comments By PostId", description = "Getting All Comments By PostId In DataBase")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    @GetMapping(
            value = {"/v1/{postId}/comments"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Set<CommentDto>> getPostComments(@PathVariable(value = "postId") Long postId) {

        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    // http://localhost:8080/api/blog/{postId}/comment/{commentId}
    @Operation(summary = "Get Comment BY PostId and CommentId", description = "Getting Particular CommentId By PostId In DataBase")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    @GetMapping(
            value = {"/v1/{postId}/comment/{commentId}"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {

        return new ResponseEntity<>(commentService.getCommentByPostId(postId, commentId), HttpStatus.OK);
    }

    // http://localhost:8080/api/blog/{postId}/comment/{commentId}
    @Operation(summary = "Update Comment By PostId and CommentId", description = "Update Comment By PostId and CommentId In DataBase")
    @ApiResponse(responseCode = "201", description = "Http Status 201 Created")
    @SecurityRequirement(name = "Jwt Authentication")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping(
            value = {"/v1/{postId}/comment/{commentId}"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CommentDto> updateCommentByPostId(@PathVariable(value = "postId") Long postId,
                                                            @PathVariable(value = "commentId") Long commentId,
                                                            @Valid @RequestBody CommentDto commentDto) {

        return new ResponseEntity<>(commentService.updateCommentBYPostId(postId, commentId, commentDto), HttpStatus.ACCEPTED);
    }

    // http://localhost:8080/api/blog/{postId}/comment/{commentId}
    @Operation(summary = "Delete Comment By CommentId", description = "Delete Comment By CommentId In DataBase")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    @SecurityRequirement(name = "Jwt Authentication")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping(
            value = {"/v1/{postId}/comment/{commentId}"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.TEXT_PLAIN_VALUE}
    )
    public ResponseEntity<String> deleteCommentByPostId(@PathVariable("postId") Long postId,
                                                        @PathVariable() Long commentId) {

        return new ResponseEntity<>(commentService.deleteCommentByPostId(postId, commentId), HttpStatus.OK);
    }
}
