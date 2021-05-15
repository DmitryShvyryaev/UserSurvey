package ru.usersSurvey.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question extends AbstractEntity {

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    @JoinColumn(name = "question_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<String> options;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_detail_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SurveyDetails surveyDetails;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    private List<Answer> answers;

    public Question() {
    }

    public Question(String text, QuestionType type, Set<String> options, SurveyDetails surveyDetails, List<Answer> answers) {
        this.text = text;
        this.type = type;
        this.options = options;
        this.surveyDetails = surveyDetails;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public Set<String> getOptions() {
        return options;
    }

    public void setOptions(Set<String> options) {
        this.options = options;
    }

    public SurveyDetails getSurveyDetails() {
        return surveyDetails;
    }

    public void setSurveyDetails(SurveyDetails surveyDetails) {
        this.surveyDetails = surveyDetails;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", type=" + type +
                ", options=" + options +
                ", surveyDetails=" + surveyDetails +
                ", answers=" + answers +
                '}';
    }
}
