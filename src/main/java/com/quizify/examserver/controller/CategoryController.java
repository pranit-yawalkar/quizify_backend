package com.quizify.examserver.controller;

import com.quizify.examserver.model.quiz.Category;
import com.quizify.examserver.service.impl.CategoryServiceImpl;
import com.quizify.examserver.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    // add category
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category category1 = this.categoryService.addCategory(category);
        return new ResponseEntity<>(category1, HttpStatus.CREATED);
    }


    // get category by id
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        Category category = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // get all categories
    @GetMapping
    public ResponseEntity<Set<Category>> getCategories() {
        Set<Category> categories = this.categoryService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // update category
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        Category category1 = this.categoryService.updateCategory(categoryId, category);
        return new ResponseEntity<>(category1, HttpStatus.OK);
    }

    // delete category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
        this.categoryService.deleteCategory(categoryId);
        ApiResponse apiResponse = new ApiResponse(true, "Category deleted successfully!");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
