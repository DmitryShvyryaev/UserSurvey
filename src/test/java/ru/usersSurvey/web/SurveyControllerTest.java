package ru.usersSurvey.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.usersSurvey.model.Question;
import ru.usersSurvey.model.QuestionType;
import ru.usersSurvey.to.AnsweredQuestionTo;
import ru.usersSurvey.to.StartSurveyTo;
import ru.usersSurvey.to.UserSurveyTo;
import ru.usersSurvey.web.json.JacksonObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SurveyControllerTest extends AbstractControllerTest {

    @Autowired
    private JacksonObjectMapper objectMapper;

    @Test
    void getAllCompleted() throws Exception {
        perform(get("/rest/surveys?userId=100000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[{\"id\":100008,\"answers\":[{\"question\":{\"id\":100004,\"text\":\"Где вы живете?\",\"type\":\"TEXT_ANSWER\",\"options\":[],\"answers\":null},\"answers\":[\"город-герой Москва\"]},{\"question\":{\"id\":100005,\"text\":\"Сколько вам лет?\",\"type\":\"TEXT_ANSWER\",\"options\":[],\"answers\":null},\"answers\":[\"30\"]}]},{\"id\":100009,\"answers\":[{\"question\":{\"id\":100006,\"text\":\"Трудовой стаж?\",\"type\":\"TEXT_ANSWER\",\"options\":[],\"answers\":null},\"answers\":[\"6 лет\"]},{\"question\":{\"id\":100007,\"text\":\"Желаемая зарплата?\",\"type\":\"ONE_OPTION_ANSWER\",\"options\":[\"10 000 долларов\",\"3 000 долларов\",\"50 000 долларов\"],\"answers\":null},\"answers\":[\"50 000 долларов\"]},{\"question\":{\"id\":100007,\"text\":\"Желаемая зарплата?\",\"type\":\"ONE_OPTION_ANSWER\",\"options\":[\"10 000 долларов\",\"3 000 долларов\",\"50 000 долларов\"],\"answers\":null},\"answers\":[\"50 000 долларов\"]},{\"question\":{\"id\":100007,\"text\":\"Желаемая зарплата?\",\"type\":\"ONE_OPTION_ANSWER\",\"options\":[\"10 000 долларов\",\"3 000 долларов\",\"50 000 долларов\"],\"answers\":null},\"answers\":[\"50 000 долларов\"]}]}]"));
    }

    @Test
    void createWithLocation() throws Exception {
        StartSurveyTo newSurvey = new StartSurveyTo(100001L, 100002L);
        String actual = perform(post("/rest/surveys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newSurvey)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"id\":100014,\"answers\":[{\"question\":{\"id\":100004,\"text\":\"Где вы живете?\",\"type\":\"TEXT_ANSWER\",\"options\":[],\"answers\":null}},{\"question\":{\"id\":100005,\"text\":\"Сколько вам лет?\",\"type\":\"TEXT_ANSWER\",\"options\":[],\"answers\":null}}]}", actual);
    }

    @Test
    void update() throws Exception {
        StartSurveyTo newSurvey = new StartSurveyTo(100001L, 100002L);
        perform(post("/rest/surveys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newSurvey)));

        AnsweredQuestionTo question1 = new AnsweredQuestionTo(new Question(100004, "Где вы живете?", QuestionType.TEXT_ANSWER), "Санкт-Петербург");
        AnsweredQuestionTo question2 = new AnsweredQuestionTo(new Question(100005, "Сколько вам лет?", QuestionType.TEXT_ANSWER), "78");
        UserSurveyTo updated = new UserSurveyTo(100014L, question1, question2);

        perform(put("/rest/surveys/100014")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}