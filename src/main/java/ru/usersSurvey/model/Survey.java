package ru.usersSurvey.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "surveys")
public class Survey extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private SurveyDetails surveyDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "survey")
    private List<Answer> answers;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public Survey() {}


}
