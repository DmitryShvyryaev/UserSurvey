package ru.usersSurvey.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.usersSurvey.model.SurveyDetails;
import ru.usersSurvey.repository.SurveyDetailsRepository;

import java.util.List;

@RestController
@RequestMapping(value = "rest/surveyDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class SurveyDetailsController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SurveyDetailsRepository surveyDetailsRepository;

    public SurveyDetailsController(SurveyDetailsRepository surveyDetailsRepository) {
        this.surveyDetailsRepository = surveyDetailsRepository;
    }

    @GetMapping
    public List<SurveyDetails> getAll() {
        logger.info("get all survey details");
        return surveyDetailsRepository.findAll();
    }

    @GetMapping("/with-questions")
    public List<SurveyDetails> getAllWithQuestions() {
        logger.info("get all survey details with questions");
        return surveyDetailsRepository.findAllWithQuestions();
    }
}
