package com.example.tprestapifinal.entities;

import jakarta.persistence.*;

@Entity
public class QuizQuestion {

    @EmbeddedId
    QuizQuestionPK id;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "QuestionID")
    private Question question;

    @ManyToOne
    @MapsId("quizId")
    @JoinColumn(name = "QuizID")
    private Quiz quiz;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Basic
    @Column(name = "SelectedOptionID")
    private Integer selectedOptionId;

    public QuizQuestionPK getId() {
        return id;
    }

    public void setId(QuizQuestionPK id) {
        this.id = id;
    }


    public Integer getSelectedOptionId() {
        return selectedOptionId;
    }

    public void setSelectedOptionId(Integer selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }
}
