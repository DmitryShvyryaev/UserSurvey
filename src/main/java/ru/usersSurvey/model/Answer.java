package ru.usersSurvey.model;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer extends AbstractEntity {

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    public Answer() {}

    public Answer(String text, Survey survey, Question question) {
        this.text = text;
        this.survey = survey;
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", survey=" + survey +
                ", question=" + question +
                '}';
    }
}
