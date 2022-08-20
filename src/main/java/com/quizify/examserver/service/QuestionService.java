package com.quizify.examserver.service;

import com.quizify.examserver.dto.QuestionDTO;
import com.quizify.examserver.dto.UserQuestionDTO;
import com.quizify.examserver.model.quiz.Question;
import com.quizify.examserver.model.quiz.Quiz;

import java.util.Set;

public interface QuestionService {

    Question addQuestion(QuestionDTO questionDTO);

    Question updateQuestion(Long queId, QuestionDTO questionDTO);

    Set<Question> getQuestions();

    Question getQuestionById(Long queId);

    Set<UserQuestionDTO> getQuestionByQuiz(Long quizId);

    void deleteQuestion(Long queId);
}
