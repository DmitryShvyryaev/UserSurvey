package ru.usersSurvey.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SurveyDetailsControllerTest extends AbstractControllerTest {

    @Test
    void getAll() throws Exception {
        perform(get("/rest/surveyDetails"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[{\"id\":100002,\"description\":\"Опрос о личном\",\"questions\":null,\"surveys\":null},{\"id\":100003,\"description\":\"Опрос о работе\",\"questions\":null,\"surveys\":null}]"));
    }

    @Test
    void getAllWithQuestions() throws Exception {
        perform(get("/rest/surveyDetails/with-questions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[{\"id\":100002,\"description\":\"Опрос о личном\",\"questions\":[{\"id\":100004,\"text\":\"Где вы живете?\",\"type\":\"TEXT_ANSWER\",\"options\":[],\"answers\":null},{\"id\":100005,\"text\":\"Сколько вам лет?\",\"type\":\"TEXT_ANSWER\",\"options\":[],\"answers\":null}],\"surveys\":null},{\"id\":100003,\"description\":\"Опрос о работе\",\"questions\":[{\"id\":100006,\"text\":\"Трудовой стаж?\",\"type\":\"TEXT_ANSWER\",\"options\":[],\"answers\":null},{\"id\":100007,\"text\":\"Желаемая зарплата?\",\"type\":\"ONE_OPTION_ANSWER\",\"options\":[\"10 000 долларов\",\"3 000 долларов\",\"50 000 долларов\"],\"answers\":null}],\"surveys\":null}]"));
    }
}