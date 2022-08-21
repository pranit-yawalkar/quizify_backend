package com.quizify.examserver.controller;

import com.quizify.examserver.dto.QuizDTO;
import com.quizify.examserver.dto.QuizResultDTO;
import com.quizify.examserver.model.quiz.Question;
import com.quizify.examserver.model.quiz.Quiz;
import com.quizify.examserver.service.QuestionService;
import com.quizify.examserver.service.impl.QuizServiceImpl;
import com.quizify.examserver.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizServiceImpl quizService;

    @Autowired
    private QuestionService questionService;

    // add the quiz
    @PostMapping
    public ResponseEntity<Quiz> addQuiz(@RequestBody QuizDTO quizDTO) {
        Quiz quiz = this.quizService.addQuiz(quizDTO);
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }

    // update the quiz
    @PutMapping("/{quizId}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long quizId, @RequestBody QuizDTO quizDTO) {
        Quiz quiz1 = this.quizService.updateQuiz(quizId, quizDTO);
        return new ResponseEntity<>(quiz1, HttpStatus.OK);
    }

    // get all quizzes
    @GetMapping("/getAll")
    public ResponseEntity<Set<Quiz>> getQuizzes() {
        Set<Quiz> quizzes = this.quizService.getQuizzes();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Set<Quiz>> getQuizzesByCategory(@PathVariable Long categoryId) {
        Set<Quiz> quizzes = this.quizService.getQuizzesByCategory(categoryId);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    // get active quizzes
    @GetMapping("/active")
    public ResponseEntity<Set<Quiz>> getActiveQuizzes() {
        Set<Quiz> quizzes = this.quizService.getActiveQuizzes();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    // get active quizzes by category
    @GetMapping("/category/active/{categoryId}")
    public ResponseEntity<Set<Quiz>> getActiveQuizzesByCategory(@PathVariable Long categoryId) {
        Set<Quiz> quizzes = this.quizService.getActiveQuizzesByCategory(categoryId);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    // get quiz by id
    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long quizId) {
        Quiz quiz = this.quizService.getQuizById(quizId);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    // delete the quiz
    @DeleteMapping("/{quizId}")
    public ResponseEntity<ApiResponse> deleteQuiz(@PathVariable Long quizId) {
        this.quizService.deleteQuiz(quizId);
        ApiResponse apiResponse = new ApiResponse(true, "Quiz deleted successfully!");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // evaluate the quiz
    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evaluateQuiz(@RequestBody List<Question> questions) {
        System.out.println(questions);

        double marks = questions.get(0).getQuiz().getMaxMarks() / questions.size();
        double marksGot = 0d;
        int correctAnswers = 0;
        int questionsAttempted = 0;

        for(Question que : questions) {
            Question question = this.questionService.getQuestionById(que.getQueId());
            System.out.println();
            if(que.getGivenAnswer()!= null && question.getAnswer().trim().equals(que.getGivenAnswer().trim())) {
                correctAnswers++;
            }

            if(que.getGivenAnswer()!=null && !que.getGivenAnswer().equals("")) {
                questionsAttempted++;
                System.out.println(questionsAttempted);
            }
        }

        marksGot = marks * correctAnswers;
        QuizResultDTO quizResultDTO = new QuizResultDTO();
        quizResultDTO.setQuizId(questions.get(0).getQuiz().getQid());
        quizResultDTO.setCorrectAnswers(correctAnswers);
        quizResultDTO.setMarks(marks);
        quizResultDTO.setMarksGot(marksGot);
        quizResultDTO.setMaxMarks(questions.get(0).getQuiz().getMaxMarks());
        quizResultDTO.setQuestionsAttempted(questionsAttempted);

        return new ResponseEntity<>(quizResultDTO, HttpStatus.OK);
    }
}


