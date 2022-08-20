package com.quizify.examserver.model.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quizify.examserver.util.StringListConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_id")
    private Long queId;

    @Column(name = "content", length = 5000)
    private String content;

    @Column(name = "image")
    private String image;

    @Convert(converter = StringListConverter.class)
    @Column(name = "options")
    private List<String> options;

    @Column(name = "answer")
    @JsonIgnore
    private String answer;

    @Transient
    private String givenAnswer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;

    public Question() {
    }

    public Question(Long queId, String content, String image, List<String> options, String answer, Quiz quiz) {
        this.queId = queId;
        this.content = content;
        this.image = image;
        this.options = options;
        this.answer = answer;
        this.quiz = quiz;
    }

    public Long getQueId() {
        return queId;
    }

    public void setQueId(Long queId) {
        this.queId = queId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "Question{" +
                "queId=" + queId +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", options=" + options +
                ", answer='" + answer + '\'' +
                ", givenAnswer='" + givenAnswer + '\'' +
                ", quiz=" + quiz +
                '}';
    }
}
