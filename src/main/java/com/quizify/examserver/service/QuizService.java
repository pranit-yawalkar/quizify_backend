package com.quizify.examserver.service;

import com.quizify.examserver.dto.QuizDTO;
import com.quizify.examserver.model.quiz.Category;
import com.quizify.examserver.model.quiz.Quiz;

import java.util.Set;

public interface QuizService {

    Quiz addQuiz(QuizDTO quizDTO);

    Quiz updateQuiz(Long qid, QuizDTO quizDTO);

    Set<Quiz> getQuizzes();

    Quiz getQuizById(Long qid);

    void deleteQuiz(Long quid);

    Set<Quiz> getQuizzesByCategory(Long categoryId);

    Set<Quiz> getActiveQuizzes();

    Set<Quiz> getActiveQuizzesByCategory(Long categoryId);
}
