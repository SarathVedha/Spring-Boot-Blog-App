package com.vedha.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "CategoryDto", description = "CategoryDTO Handles Category CURD Operations")
public class CategoryDto {

    private Long id;

    @Schema(name = "name", description = "Category Name")
    @NotEmpty(message = "Category Name Should Not Be Empty")
    @Size(min = 5, max = 15, message = "Category Name Should Be In 5 to 15 Chars")
    private String name;

    @Schema(name = "description", description = "Category Description")
    @NotEmpty(message = "Category Message Should Not Be Empty")
    @Size(max = 30, message = "CategoryDescription Should Be In Below 30 Chars")
    private String description;
}
