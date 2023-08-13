package com.vedha.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "PostDTO", description = "PostDTO Handles Post CURD Operations")
public class PostDto {

    private Long id;

    @Schema(name = "title", description = "Post Title")
    @NotEmpty(message = "Post Title Should Not Be Empty")
    @Size(min = 2, message = "Post Title Should Be At least 2 chars")
    private String title;

    @Schema(name = "description", description = "Post Description")
    @NotEmpty(message = "Post Description Should Not Empty")
    @Size(min = 10, message = "Post Description At Least 10 chars")
    private String description;

    @Schema(name = "content", description = "Post Content")
    @NotEmpty(message = "Post Content Should Not Be Empty")
    private String content;

    private Set<CommentDto> commentEntities;

    @Schema(name = "categoryId", description = "Post Category")
    @NotNull(message = "Post Category Should Not Be Empty")
    private Long categoryId;
}
