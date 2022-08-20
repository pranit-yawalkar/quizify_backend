package com.quizify.examserver.service.impl;

import com.quizify.examserver.exception.ResourceNotFoundException;
import com.quizify.examserver.model.quiz.Category;
import com.quizify.examserver.repository.CategoryRepository;
import com.quizify.examserver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long cid, Category category) {

        Category category1 = this.categoryRepository.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category1.setTitle(category.getTitle());
        category1.setDescription(category.getDescription());
        category1.setQuizzes(category.getQuizzes());
        return this.categoryRepository.save(category1);
    }

    @Override
    public Set<Category> getCategories() {
        return new LinkedHashSet<>(this.categoryRepository.findAll());
    }

    @Override
    public Category getCategoryById(Long cid) {
        return this.categoryRepository.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategory(Long cid) {
        Category category = this.categoryRepository.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        this.categoryRepository.delete(category);
    }
}
