package ru.usersSurvey.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "survey_details")
public class SurveyDetails extends AbstractEntity {

    @Column(name = "description", nullable = false)
    private String description;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyDetails")
    private List<Question> questions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyDetails")
    private List<Survey> surveys;

    public SurveyDetails() {}

    public SurveyDetails(String description, List<Question> questions) {
        this.description = description;
        this.questions = questions;
    }

    public SurveyDetails(long id, String description, List<Question> questions) {
        this.id = id;
        this.description = description;
        this.questions = questions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "SurveyDetails{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", questions=" + questions +
                '}';
    }
}
