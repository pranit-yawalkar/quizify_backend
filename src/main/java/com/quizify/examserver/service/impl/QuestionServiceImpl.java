package com.quizify.examserver.service.impl;


import com.quizify.examserver.dto.QuestionDTO;
import com.quizify.examserver.dto.UserQuestionDTO;
import com.quizify.examserver.exception.ResourceNotFoundException;
import com.quizify.examserver.model.quiz.Question;
import com.quizify.examserver.model.quiz.Quiz;
import com.quizify.examserver.model.user.User;
import com.quizify.examserver.repository.QuestionRepository;
import com.quizify.examserver.service.QuestionService;
import com.quizify.examserver.util.StringListConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizServiceImpl quizService;

    public Question getQuestionByDTO(QuestionDTO questionDTO) {
        System.out.println(questionDTO);
        Question question = new Question();
        System.out.println(questionDTO.getContent());
        question.setContent(questionDTO.getContent());
        question.setOptions(questionDTO.getOptions());
        question.setAnswer(questionDTO.getAnswer());
        question.setImage(questionDTO.getImage());
        question.setQuiz(this.quizService.getQuizById(questionDTO.getQuizId()));
        System.out.println(question);
        return question;
    }

    public UserQuestionDTO getUserQuestionDTO(Question question) {
        UserQuestionDTO userQuestionDTO = new UserQuestionDTO();
        userQuestionDTO.setQueId(question.getQueId());
        userQuestionDTO.setContent(question.getContent());
        userQuestionDTO.setImage(question.getImage());
        userQuestionDTO.setOptions(question.getOptions());
        userQuestionDTO.setGivenAnswer(question.getGivenAnswer());
        userQuestionDTO.setQuiz(this.quizService.getQuizById(question.getQuiz().getQid()));
        return userQuestionDTO;
    }

    @Override
    public Question addQuestion(QuestionDTO questionDTO) {
        Question question = this.getQuestionByDTO(questionDTO);
        System.out.println(question);
        return this.questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Long queId, QuestionDTO questionDTO) {
        Question question = this.getQuestionByDTO(questionDTO);
        Question question1 = this.questionRepository.findById(queId)
                .orElseThrow(() -> new ResourceNotFoundException("Question does not exist"));

        question1.setContent(question.getContent());
        question1.setAnswer(question.getAnswer());
        question1.setImage(question.getImage());
        question1.setOptions(question.getOptions());
        question1.setQuiz(question.getQuiz());

        return this.questionRepository.save(question1);
    }

    @Override
    public Set<Question> getQuestions() {
        return new HashSet<>(this.questionRepository.findAll());
    }

    @Override
    public Question getQuestionById(Long queId) {
        return this.questionRepository.findById(queId)
                .orElseThrow(() -> new ResourceNotFoundException("Question does not exist"));
    }

    @Override
    public Set<UserQuestionDTO> getQuestionByQuiz(Long quizId) {
        Quiz quiz = this.quizService.getQuizById(quizId);
        Set<Question> questions = quiz.getQuestions();
        if(questions.size()>quiz.getNumberOfQuestions()) {
            questions = questions
                    .stream()
                    .limit(quiz.getNumberOfQuestions())
                    .collect(Collectors.toSet());
        }

        Set<UserQuestionDTO> userQuestionDTOS = new HashSet<>();
        questions.forEach(question -> {
            UserQuestionDTO userQuestionDTO = getUserQuestionDTO(question);
            userQuestionDTOS.add(userQuestionDTO);
        });

        return userQuestionDTOS;
    }

    @Override
    public void deleteQuestion(Long queId) {
        Question question = this.questionRepository.findById(queId)
                .orElseThrow(() -> new ResourceNotFoundException("Question does not exist"));

        this.questionRepository.delete(question);
    }
}
