package ru.usersSurvey.to;

public class StartSurveyTo {
    private Long userId;
    private Long surveyDetailId;

    public StartSurveyTo() {
    }

    public StartSurveyTo(Long userId, Long surveyDetailId) {
        this.userId = userId;
        this.surveyDetailId = surveyDetailId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSurveyDetailId() {
        return surveyDetailId;
    }

    public void setSurveyDetailId(Long surveyDetailId) {
        this.surveyDetailId = surveyDetailId;
    }

    @Override
    public String toString() {
        return "StartSurveyTo{" +
                "userId=" + userId +
                ", surveyDetailId=" + surveyDetailId +
                '}';
    }
}
