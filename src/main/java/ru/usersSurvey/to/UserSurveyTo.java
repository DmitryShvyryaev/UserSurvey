package ru.usersSurvey.to;

import ru.usersSurvey.model.Survey;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserSurveyTo {
    private Long id;
    private List<AnsweredQuestionTo> answers;

    public UserSurveyTo() {
    }

    public UserSurveyTo(Long id) {
        this.id = id;
    }

    public UserSurveyTo(Long id, AnsweredQuestionTo... answers) {
        this.id = id;
        this.answers = List.of(answers);
    }

    public static UserSurveyTo getFromSurvey(Survey survey) {
        UserSurveyTo to = new UserSurveyTo(survey.getId());
        to.answers = survey.getSurveyDetails().getQuestions().stream()
                .map(AnsweredQuestionTo::new)
                .collect(Collectors.toList());
        return to;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<AnsweredQuestionTo> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, List<String>> dbAnswers) {
        answers.forEach(answer -> answer.setAnswers(dbAnswers.get(answer.getQuestion().getId())));
    }

    @Override
    public String toString() {
        return "UserSurveyTo{" +
                "id=" + id +
                ", answers=" + answers +
                '}';
    }
}
