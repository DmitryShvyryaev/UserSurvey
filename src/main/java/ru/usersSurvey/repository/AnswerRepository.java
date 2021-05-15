package ru.usersSurvey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.usersSurvey.model.Answer;
import ru.usersSurvey.to.AnswerTo;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Transactional
    default List<AnswerTo> getToBySurveyId(long id) {
        return findAllBySurveyId(id).stream()
                .map(answer -> new AnswerTo(answer.getQuestion().getId(), answer.getText()))
                .collect(Collectors.toList());
    }

    List<Answer> findAllBySurveyId(long id);
}
