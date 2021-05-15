package ru.usersSurvey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.usersSurvey.model.Survey;

@Transactional(readOnly = true)
public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
