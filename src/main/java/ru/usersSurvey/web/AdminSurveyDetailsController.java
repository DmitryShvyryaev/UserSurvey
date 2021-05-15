package ru.usersSurvey.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.usersSurvey.model.SurveyDetails;
import ru.usersSurvey.repository.SurveyDetailsRepository;

import java.net.URI;

import static ru.usersSurvey.util.ValidationUtil.*;

@RestController
@RequestMapping(value = "rest/admin/surveyDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminSurveyDetailsController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SurveyDetailsRepository surveyDetailsRepository;

    public AdminSurveyDetailsController(SurveyDetailsRepository surveyDetailsRepository) {
        this.surveyDetailsRepository = surveyDetailsRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SurveyDetails> create(@RequestBody SurveyDetails surveyDetails) {
        logger.info("Create new SurveyDetails {}", surveyDetails);
        checkNew(surveyDetails);
        SurveyDetails created = surveyDetailsRepository.save(surveyDetails);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("rest/admin/surveyDetails" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long id, @RequestBody SurveyDetails surveyDetails) {
        logger.info("update survey details {} with id {}", surveyDetails, id);
        assureIdConsistent(surveyDetails, id);
        surveyDetailsRepository.save(surveyDetails);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        logger.info("delete survey details with id {}", id);
        surveyDetailsRepository.deleteById(id);
    }
}
