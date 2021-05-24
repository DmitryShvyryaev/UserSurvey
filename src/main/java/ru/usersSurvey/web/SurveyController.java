package ru.usersSurvey.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.usersSurvey.model.*;
import ru.usersSurvey.repository.*;
import ru.usersSurvey.to.AnswerTo;
import ru.usersSurvey.to.AnsweredQuestionTo;
import ru.usersSurvey.to.StartSurveyTo;
import ru.usersSurvey.to.UserSurveyTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "rest/surveys", produces = MediaType.APPLICATION_JSON_VALUE)
public class SurveyController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SurveyRepository surveyRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final SurveyDetailsRepository surveyDetailsRepository;
    private final QuestionRepository questionRepository;

    public SurveyController(SurveyRepository surveyRepository, AnswerRepository answerRepository, UserRepository userRepository, SurveyDetailsRepository surveyDetailsRepository, QuestionRepository questionRepository) {
        this.surveyRepository = surveyRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.surveyDetailsRepository = surveyDetailsRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public List<UserSurveyTo> getAllCompleted(@RequestParam long userId) {
        logger.info("Get all completed surveys for user with id {}", userId);
        List<Survey> surveys = surveyRepository.findAllByEndDateIsNotNullAndUserId(userId);
        List<UserSurveyTo> tos = surveys.stream()
                .map(UserSurveyTo::getFromSurvey)
                .collect(Collectors.toList());
        return tos.stream()
                .peek(to -> to.setAnswers(getAnswers(to.getId())))
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSurveyTo> createWithLocation(@RequestBody StartSurveyTo startSurveyTo) {
        logger.info("User with id {} start survey detail with id {}", startSurveyTo.getUserId(), startSurveyTo.getSurveyDetailId());
        User owner = startSurveyTo.getUserId() == null ? null : userRepository.getOne(startSurveyTo.getUserId());
        SurveyDetails surveyDetails = surveyDetailsRepository.getOne(startSurveyTo.getSurveyDetailId());
        Survey created = surveyRepository.saveAndReturn(new Survey(surveyDetails, owner, LocalDate.now()));
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("rest/surveys" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        UserSurveyTo to = UserSurveyTo.getFromSurvey(created);
        return ResponseEntity.created(uri).body(to);
    }

    @Transactional
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long id, @RequestBody UserSurveyTo to) {
        logger.info("Update survey {} with id {}", to, id);
        checkCompleteSurvey(id);
        saveIfComplete(to);
        Survey survey = surveyRepository.getOne(to.getId());
        for (AnsweredQuestionTo answeredQuestionTo : to.getAnswers()) {
            Question question = questionRepository.getOne(answeredQuestionTo.getQuestion().getId());
            if (answeredQuestionTo.getAnswers() != null) {
                for (String text : answeredQuestionTo.getAnswers()) {
                    answerRepository.save(new Answer(text, survey, question));
                }
            }
        }
    }

    private Map<Long, List<String>> getAnswers(long surveyId) {
        logger.info("Get answers for survey with id {}", surveyId);
        List<AnswerTo> tos = answerRepository.getToBySurveyId(surveyId);
        return tos.stream()
                .collect(Collectors.groupingBy(AnswerTo::getQuestionId,
                        Collectors.mapping(AnswerTo::getText, Collectors.toList())));
    }

    private void saveIfComplete(UserSurveyTo to) {
        for (AnsweredQuestionTo answer : to.getAnswers()) {
            if (answer.getAnswers() == null)
                return;
        }
        Survey survey = surveyRepository.getById(to.getId());
        survey.setEndDate(LocalDate.now());
        surveyRepository.save(survey);
    }

    private void checkCompleteSurvey(long id) {
        Survey survey = surveyRepository.findById(id).orElse(null);
        if (survey == null || survey.getEndDate() != null)
            throw new IllegalArgumentException();
    }
}
