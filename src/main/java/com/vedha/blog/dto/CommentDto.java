package com.vedha.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "CommentDto", description = "CommentDTO Handles Comment CURD Operations")
public class CommentDto {

    private Long id;

    @Schema(name = "name", description = "Commenter Name")
    @NotEmpty(message = "Comment Name Should Not Be Empty")
    @Size(min = 5, message = "Comment Name Should Be Minimum 5 chars")
    private String name;

    @Schema(name = "email", description = "Commenter Email")
    @NotEmpty(message = "Comment Email Should Not Be Empty")
    @Email(message = "Invalid Comment Email")
    private String email;

    @Schema(name = "body", description = "Comment Message")
    @NotEmpty(message = "Comment Body Should Not Be Empty")
    @Size(min = 10, message = "Comment Body Should Be Minimum 10 chars")
    private String body;

}
