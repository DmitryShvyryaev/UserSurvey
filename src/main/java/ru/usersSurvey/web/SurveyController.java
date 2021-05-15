package ru.usersSurvey.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.usersSurvey.model.Survey;
import ru.usersSurvey.model.SurveyDetails;
import ru.usersSurvey.model.User;
import ru.usersSurvey.repository.AnswerRepository;
import ru.usersSurvey.repository.SurveyDetailsRepository;
import ru.usersSurvey.repository.SurveyRepository;
import ru.usersSurvey.repository.UserRepository;
import ru.usersSurvey.to.AnswerTo;
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

    public SurveyController(SurveyRepository surveyRepository, AnswerRepository answerRepository, UserRepository userRepository, SurveyDetailsRepository surveyDetailsRepository) {
        this.surveyRepository = surveyRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.surveyDetailsRepository = surveyDetailsRepository;
    }

    @GetMapping("/{userId}")
    public List<UserSurveyTo> getAllCompleted(@PathVariable long userId) {
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

    private Map<Long, List<String>> getAnswers(long surveyId) {
        logger.info("Get answers for survey with id {}", surveyId);
        List<AnswerTo> tos = answerRepository.getToBySurveyId(surveyId);
        return tos.stream()
                .collect(Collectors.groupingBy(AnswerTo::getQuestionId,
                        Collectors.mapping(AnswerTo::getText, Collectors.toList())));
    }
}
