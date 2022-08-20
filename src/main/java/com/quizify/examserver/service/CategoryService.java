package com.quizify.examserver.service;

import com.quizify.examserver.model.quiz.Category;

import java.util.Set;

public interface CategoryService {

    Category addCategory(Category category);

    Category updateCategory(Long cid, Category category);

    Set<Category> getCategories();

    Category getCategoryById(Long cid);

    void deleteCategory(Long cid);
}
