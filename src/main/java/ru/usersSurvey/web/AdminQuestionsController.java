package ru.usersSurvey.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.usersSurvey.model.Question;
import ru.usersSurvey.repository.QuestionRepository;

import java.net.URI;
import java.util.List;

import static ru.usersSurvey.util.ValidationUtil.*;

@RestController
@RequestMapping(value = "rest/admin/surveyDetails/{surveyId}/questions", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminQuestionsController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final QuestionRepository questionRepository;

    public AdminQuestionsController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public List<Question> getAll(@PathVariable long surveyId) {
        logger.info("Get all questions for survey details with id {}", surveyId);
        return questionRepository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Question> create(@PathVariable long surveyId, @RequestBody Question question) {
        logger.info("Create new question {} for survey detail with id {}", question, surveyId);
        checkNew(question);
        Question created = questionRepository.save(question);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("rest/admin/surveyDetails/{surveyId}/questions/{id}")
                .buildAndExpand(surveyId, created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long surveyId, @PathVariable long id, @RequestBody Question question) {
        logger.info("Update question {} with id {} for survey detail with id {}", question, id, surveyId);
        assureIdConsistent(question, id);
        questionRepository.save(question);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long surveyId, @PathVariable long id) {
        logger.info("Delete question with id {} for survey details with id {}", id, surveyId);
        questionRepository.delete(id, surveyId);
    }
}
