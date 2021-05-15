package ru.usersSurvey.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "surveys")
public class Survey extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_detail_id", nullable = false)
    private SurveyDetails surveyDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "survey")
    private List<Answer> answers;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public Survey() {}

    public Survey(SurveyDetails surveyDetails, User user, LocalDate startDate) {
        this.surveyDetails = surveyDetails;
        this.user = user;
        this.startDate = startDate;
    }

    public SurveyDetails getSurveyDetails() {
        return surveyDetails;
    }

    public void setSurveyDetails(SurveyDetails surveyDetails) {
        this.surveyDetails = surveyDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", surveyDetails=" + surveyDetails +
                ", user=" + user +
                ", answers=" + answers +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
