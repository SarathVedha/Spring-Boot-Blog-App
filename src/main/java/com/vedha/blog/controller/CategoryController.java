package com.vedha.blog.controller;

import com.vedha.blog.dto.CategoryDto;
import com.vedha.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@AllArgsConstructor
@Tag(name = "CURD REST APIs For Category")
@Slf4j
public class CategoryController {

    private CategoryService categoryService;

    // http://localhost:8080/api/blog/category
    @Operation(summary = "Create Category", description = "Create Category In DataBase")
    @ApiResponse(responseCode = "201", description = "Http Status 201 Created")
    @SecurityRequirement(name = "Jwt Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = { "/v1/category" },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Valid CategoryDto categoryDto) {

        log.info("categoryDto : " + categoryDto);

        CategoryDto savedCategory = categoryService.addCategory(categoryDto);

        log.info("savedCategory : " + savedCategory);

        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/blog/category/{categoryId}
    @Operation(summary = "Get Category By CategoryId", description = "Getting Category By Id In DataBase")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    @GetMapping(value = { "/v1/category/{categoryId}" },
            consumes = { MediaType.ALL_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Long categoryId) {

        log.info("categoryId : " + categoryId);

        CategoryDto category = categoryService.getCategory(categoryId);

        log.info("category : " + category);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // http://localhost:8080/api/blog/allCategory
    @Operation(summary = "Get All Category", description = "Get All Category in DataBase")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    @GetMapping(value = { "/v1/allCategory" },
            consumes = { MediaType.ALL_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<List<CategoryDto>> getAllCategory(){

        log.info("Get All Category");

        List<CategoryDto> allCategory = categoryService.getAllCategory();

        log.info("allCategory : " + allCategory);

        return ResponseEntity.ok(allCategory);
    }

    // http://localhost:8080/api/blog/updateCategory/{categoryId}
    @Operation(summary = "Update Category By CategoryId", description = "Update Category In DataBase")
    @ApiResponse(responseCode = "201", description = "Http Status 201 Created")
    @SecurityRequirement(name = "Jwt Authentication")
    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = { "/v1/updateCategory/{categoryId}" },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("categoryId") Long categoryId, @Valid @RequestBody CategoryDto categoryDto) {

        log.info("categoryId : " + categoryId);
        log.info("categoryDto : " + categoryDto);

        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);

        log.info("updatedCategory : " + updatedCategory);

        return new ResponseEntity<>(updatedCategory, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/blog/deleteCategory/{Id}
    @Operation(summary = "Delete Category By CategoryId", description = "Delete Category In DataBase")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    @SecurityRequirement(name = "Jwt Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = { "/v1/deleteCategory/{categoryId}" },
            consumes = { MediaType.ALL_VALUE },
            produces = { MediaType.ALL_VALUE }
    )
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId) {

        log.info("categoryId : " + categoryId);

        String message = categoryService.deleteCategory(categoryId);

        log.info("message : " + message);

        return ResponseEntity.ok(message);
    }

}
