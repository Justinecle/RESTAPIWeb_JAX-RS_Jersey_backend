package com.example.tprestapifinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Options {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "OptionID")
    private Integer optionId;
    @Basic
    @Column(name = "texte")
    private String texte;
    @Basic
    @Column(name = "EstVrai")
    private Boolean estVrai;
    @Basic
    @Column(name = "QuestionID", insertable=false, updatable=false)
    private Integer questionId;
    @ManyToOne
    @JoinColumn(name = "QuestionID", referencedColumnName = "QuestionID")
    @JsonIgnore
    private Question questionByQuestionId;

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Boolean getEstVrai() {
        return estVrai;
    }

    public void setEstVrai(Boolean estVrai) {
        this.estVrai = estVrai;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Options options = (Options) o;

        if (optionId != null ? !optionId.equals(options.optionId) : options.optionId != null) return false;
        if (texte != null ? !texte.equals(options.texte) : options.texte != null) return false;
        if (estVrai != null ? !estVrai.equals(options.estVrai) : options.estVrai != null) return false;
        if (questionId != null ? !questionId.equals(options.questionId) : options.questionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = optionId != null ? optionId.hashCode() : 0;
        result = 31 * result + (texte != null ? texte.hashCode() : 0);
        result = 31 * result + (estVrai != null ? estVrai.hashCode() : 0);
        result = 31 * result + (questionId != null ? questionId.hashCode() : 0);
        return result;
    }

    public Question getQuestionByQuestionId() {
        return questionByQuestionId;
    }

    public void setQuestionByQuestionId(Question questionByQuestionId) {
        this.questionByQuestionId = questionByQuestionId;
    }
}
