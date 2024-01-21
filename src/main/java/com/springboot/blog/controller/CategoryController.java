package com.springboot.blog.controller;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "CRUD REST APIs for Category Resource")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //add category REST api
    @Operation(
            summary = "Create Category REST API",
            description = "Create Category REST API is used to save Category into database"
    )
    @ApiResponse
            (
                    responseCode = "201",
                    description = "Http Status 201 CREATED"
            )
    @SecurityRequirement(name = "Bear Authentication")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
    CategoryDto savedCategoryDto = categoryService.addCategory(categoryDto);
    return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
    }

    //Build category REST api
    @Operation(
            summary = "Get Category by id REST API",
            description = "Get Category by id  REST API is used to get a Category from database"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId){
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(categoryDto);
    }
    //Build getAll category REST API
    @Operation(
            summary = "Get all Category REST API",
            description = "Get all Category REST API is used to get all Category from database"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    //Build update REST api for category
    @Operation(
            summary = "Update Category by id REST API",
            description = "Update Category by id  REST API is used to Update a Category in database"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @SecurityRequirement(name = "Bear Authentication")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id,@RequestBody CategoryDto categoryDto){
    return ResponseEntity.ok(categoryService.updateCategory(id,categoryDto));
    }

    //Build delete category REST API
    @Operation(
            summary = "Delete Category by id REST API",
            description = "Delete Category by id  REST API is used delete a Category from database"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @SecurityRequirement(name = "Bear Authentication")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted Successfully");
    }
}
