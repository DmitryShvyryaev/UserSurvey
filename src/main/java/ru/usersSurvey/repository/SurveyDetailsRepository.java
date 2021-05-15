package ru.usersSurvey.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.usersSurvey.model.SurveyDetails;

import java.util.List;

@Transactional(readOnly = true)
public interface SurveyDetailsRepository extends JpaRepository<SurveyDetails, Long> {

    @EntityGraph(attributePaths = {"questions"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT DISTINCT s FROM SurveyDetails s LEFT OUTER JOIN s.questions")
    List<SurveyDetails> findAllWithQuestions();

    SurveyDetails findById(long id);
}
