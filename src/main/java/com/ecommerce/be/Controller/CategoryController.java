package com.ecommerce.be.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.be.Entity.Category;
import com.ecommerce.be.Service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }




    @Operation(summary = "Get all categories level 3", tags = { "Category" }, responses = {
        @ApiResponse(responseCode = "200", description = "Return a list of categories or empty list", content = {
            @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Category.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
    })

    @GetMapping()
    public ResponseEntity<List<Category>> getCategoriesLevel3() {
        List<Category> categories = categoryService.getCategoryByLevel(3);
        return ResponseEntity.ok(categories);
    }




    @Operation(summary = "Get all children of a category", tags = { "Category" }, responses = {
        @ApiResponse(responseCode = "200", description = "Return a list of categories or empty list if the category has no children", content = {
            @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Category.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
    })
    // TODO: validate request body
    @GetMapping("{categoryId}/children")
    public ResponseEntity<List<Category>> getCategoryChildren(@PathVariable Integer categoryId) {
        List<Category> categoryChildren = categoryService.getCategoryChildren(categoryId);
        return ResponseEntity.ok(categoryChildren);
    }




    @Operation(summary = "Get all categories at a level", tags = { "Category" }, responses = {
        @ApiResponse(responseCode = "200", description = "Return a list of categories", content = {
            @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Category.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
    })

    @GetMapping("{level}")
    public ResponseEntity<List<Category>> getCategoryByLevel(@PathVariable Integer level) {
        List<Category> gifts = categoryService.getCategoryByLevel(level);
        return ResponseEntity.ok(gifts);
    }
}
