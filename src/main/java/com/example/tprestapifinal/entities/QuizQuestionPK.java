package com.example.tprestapifinal.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class QuizQuestionPK implements Serializable {
    @Column(name = "QuestionID")
    private Integer questionId;
    @Column(name = "QuizID")
    private Integer quizId;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuizQuestionPK that = (QuizQuestionPK) o;

        if (questionId != null ? !questionId.equals(that.questionId) : that.questionId != null) return false;
        if (quizId != null ? !quizId.equals(that.quizId) : that.quizId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = questionId != null ? questionId.hashCode() : 0;
        result = 31 * result + (quizId != null ? quizId.hashCode() : 0);
        return result;
    }
}
