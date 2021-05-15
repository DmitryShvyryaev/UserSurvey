package ru.usersSurvey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.usersSurvey.model.Question;

import java.util.List;

@Transactional(readOnly = true)
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Question q WHERE q.id=:id AND q.surveyDetails.id=:surveyId")
    void delete(@Param("id") long id, @Param("surveyId") long surveyId);

    List<Question> findAllBySurveyDetailsId(long id);

    Question findByIdAndSurveyDetailsId(long id, long surveyId);
}
