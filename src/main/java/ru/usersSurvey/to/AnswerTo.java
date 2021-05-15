package ru.usersSurvey.to;

public class AnswerTo {
    private Long questionId;
    private String text;

    public AnswerTo(Long questionId, String text) {
        this.questionId = questionId;
        this.text = text;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "AnswerTo{" +
                "questionId=" + questionId +
                ", text='" + text + '\'' +
                '}';
    }
}
