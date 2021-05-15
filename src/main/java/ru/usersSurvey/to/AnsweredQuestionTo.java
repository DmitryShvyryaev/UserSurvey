package ru.usersSurvey.to;

import ru.usersSurvey.model.Question;

import java.util.List;

public class AnsweredQuestionTo {
    private Question question;
    private List<String> answers;

    public AnsweredQuestionTo() {
    }

    public AnsweredQuestionTo(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "AnsweredQuestionTo{" +
                "question=" + question +
                ", answers=" + answers +
                '}';
    }
}
