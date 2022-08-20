package com.quizify.examserver.dto;

import java.util.List;

public class QuestionDTO {
    private Long queId;
    private String content;
    private String image;
    private List<String> options;
    private String answer;

    private String givenAnswer;
    private Long quizId;


    public QuestionDTO(Long queId, String content, String image, List<String> options, String answer, Long quizId) {
        this.queId = queId;
        this.content = content;
        this.image = image;
        this.options = options;
        this.answer = answer;
        this.quizId = quizId;
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

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }


    @Override
    public String toString() {
        return "QuestionDTO{" +
                "queId=" + queId +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", options=" + options +
                ", answer='" + answer + '\'' +
                ", givenAnswer='" + givenAnswer + '\'' +
                ", quizId=" + quizId +
                '}';
    }
}
