package com.quizify.examserver.repository;

import com.quizify.examserver.model.quiz.Category;
import com.quizify.examserver.model.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Set<Quiz> findByCategory(Category category);
    Set<Quiz> findByActive(Boolean active);
    Set<Quiz> findByCategoryAndActive(Category category, Boolean active);
}
