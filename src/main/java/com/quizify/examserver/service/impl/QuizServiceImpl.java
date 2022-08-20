package com.quizify.examserver.service.impl;

import com.quizify.examserver.dto.QuizDTO;
import com.quizify.examserver.exception.ResourceNotFoundException;
import com.quizify.examserver.model.quiz.Category;
import com.quizify.examserver.model.quiz.Quiz;
import com.quizify.examserver.repository.QuizRepository;
import com.quizify.examserver.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CategoryServiceImpl categoryService;


    public Quiz getQuizByDTO(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setMaxMarks(quizDTO.getMaxMarks());
        quiz.setNumberOfQuestions(quizDTO.getNumberOfQuestions());
        quiz.setImage(quizDTO.getImage());
        quiz.setActive(quizDTO.isActive());
        quiz.setCategory(this.categoryService.getCategoryById(quizDTO.getCategoryId()));
        return quiz;
    }

    @Override
    public Quiz addQuiz(QuizDTO quizDTO) {
        Quiz quiz = this.getQuizByDTO(quizDTO);
        return this.quizRepository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Long qid, QuizDTO quizDTO) {
        Quiz quiz = this.getQuizByDTO(quizDTO);
        Quiz quiz1 = this.quizRepository.findById(qid)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz does not exists"));

        quiz1.setTitle(quiz.getTitle());
        quiz1.setDescription(quiz.getDescription());
        quiz1.setMaxMarks(quiz.getMaxMarks());
        quiz1.setNumberOfQuestions(quiz.getNumberOfQuestions());
        quiz1.setImage(quiz.getImage());
        quiz1.setActive(quiz.isActive());
        quiz1.setCategory(quiz.getCategory());

        return this.quizRepository.save(quiz1);
    }

    @Override
    public Set<Quiz> getQuizzes() {
        return new HashSet<>(this.quizRepository.findAll());
    }

    @Override
    public Set<Quiz> getQuizzesByCategory(Long categoryId) {
        Category category = this.categoryService.getCategoryById(categoryId);
        return this.quizRepository.findByCategory(category);
    }

    @Override
    public Set<Quiz> getActiveQuizzes() {
        return this.quizRepository.findByActive(true);
    }

    @Override
    public Set<Quiz> getActiveQuizzesByCategory(Long categoryId) {
        Category category = this.categoryService.getCategoryById(categoryId);
        return this.quizRepository.findByCategoryAndActive(category, true);
    }

    @Override
    public Quiz getQuizById(Long qid) {
        return this.quizRepository.findById(qid)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz does not exists"));
    }

    @Override
    public void deleteQuiz(Long qid) {
        Quiz quiz = this.quizRepository.findById(qid)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz does not exists"));
        this.quizRepository.delete(quiz);
    }
}
