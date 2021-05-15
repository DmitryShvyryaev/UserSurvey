package ru.usersSurvey.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.usersSurvey.model.Survey;

import java.util.List;

@Transactional(readOnly = true)
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @EntityGraph(attributePaths = {"surveyDetails", "surveyDetails.questions", "surveyDetails.questions.options"})
    List<Survey> findAllByEndDateIsNotNullAndUserId(long userId);

    @Transactional
    default Survey saveAndReturn(Survey survey) {
        Survey created = save(survey);
        return getById(created.getId());
    }

    @EntityGraph(attributePaths = {"surveyDetails", "surveyDetails.questions", "surveyDetails.questions.options"})
    Survey getById(Long id);
}
