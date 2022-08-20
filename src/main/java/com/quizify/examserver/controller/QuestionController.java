package com.quizify.examserver.controller;

import com.quizify.examserver.dto.QuestionDTO;
import com.quizify.examserver.dto.UserQuestionDTO;
import com.quizify.examserver.model.quiz.Question;
import com.quizify.examserver.model.quiz.Quiz;
import com.quizify.examserver.service.impl.QuestionServiceImpl;
import com.quizify.examserver.service.impl.QuizServiceImpl;
import com.quizify.examserver.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionServiceImpl questionService;

    @Autowired
    private QuizServiceImpl quizService;

    // add the question
    @PostMapping
    public ResponseEntity<Question> addQuestion(@RequestBody QuestionDTO questionDTO) {
        System.out.println(questionDTO.getContent() + ": Content");
        Question question1 = this.questionService.addQuestion(questionDTO);
        return new ResponseEntity<>(question1, HttpStatus.CREATED);
    }

    // update the question
    @PutMapping("/{questionId}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long questionId, @RequestBody QuestionDTO questionDTO) {
        Question question1 = this.questionService.updateQuestion(questionId, questionDTO);
        return new ResponseEntity<>(question1, HttpStatus.OK);
    }

    // get all questions
    @GetMapping
    public ResponseEntity<Set<Question>> getQuestions() {
        Set<Question> questions = this.questionService.getQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    // get limited question of a quiz
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<Set<UserQuestionDTO>> getQuestionsByQuiz(@PathVariable Long quizId) {
        Set<UserQuestionDTO> userQuestionDTOS = this.questionService.getQuestionByQuiz(quizId);
        return new ResponseEntity<>(userQuestionDTOS, HttpStatus.OK);
    }

    // get all question of a quiz
    @GetMapping("/quiz/{quizId}/getAll")
    public ResponseEntity<Set<Question>> getAllQuestionsByQuiz(@PathVariable Long quizId) {
        Quiz quiz = this.quizService.getQuizById(quizId);
        Set<Question> questions = quiz.getQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    // get question by id
    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long questionId) {
        Question question = this.questionService.getQuestionById(questionId);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    // delete the question
    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable Long questionId) {
        this.questionService.deleteQuestion(questionId);
        ApiResponse apiResponse = new ApiResponse(true, "Question deleted successfully!");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
