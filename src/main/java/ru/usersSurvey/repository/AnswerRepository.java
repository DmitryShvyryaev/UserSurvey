package ru.usersSurvey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.usersSurvey.model.Answer;

@Transactional(readOnly = true)
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
