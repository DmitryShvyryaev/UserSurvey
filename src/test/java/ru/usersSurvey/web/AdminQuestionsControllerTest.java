package ru.usersSurvey.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.usersSurvey.model.Question;
import ru.usersSurvey.model.QuestionType;
import ru.usersSurvey.model.Role;
import ru.usersSurvey.model.User;
import ru.usersSurvey.repository.QuestionRepository;
import ru.usersSurvey.web.json.JacksonObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminQuestionsControllerTest extends AbstractControllerTest {

    private final User admin = new User("admin", "admin", Role.USER, Role.ADMIN);
    private final User user = new User("user", "user", Role.USER);
    @Autowired
    private JacksonObjectMapper objectMapper;
    @Autowired
    private QuestionRepository repository;

    @Test
    void getAll() throws Exception {
        perform(get("/rest/admin/surveyDetails/100002/questions")
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[{\"id\":100004,\"text\":\"Где вы живете?\",\"type\":\"TEXT_ANSWER\",\"options\":[],\"answers\":null},{\"id\":100005,\"text\":\"Сколько вам лет?\",\"type\":\"TEXT_ANSWER\",\"options\":[],\"answers\":null}]"));
    }

    @Test
    void create() throws Exception {
        Question newQuestion = new Question("А что за вопрос?", QuestionType.TEXT_ANSWER);
        String created = perform(post("/rest/admin/surveyDetails/100002/questions")
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newQuestion)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        assertEquals("{\"id\":100010,\"text\":\"А что за вопрос?\",\"type\":\"TEXT_ANSWER\"}", created);
    }

    @Test
    void update() throws Exception {
        Question updated = new Question(100004, "Новый текст", QuestionType.TEXT_ANSWER);
        perform(put("/rest/admin/surveyDetails/100002/questions/100004")
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertEquals("Новый текст", repository.findByIdAndSurveyDetailsId(100004L, 100002L).getText());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete("/rest/admin/surveyDetails/100002/questions/100004")
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertNull(repository.findByIdAndSurveyDetailsId(100004L, 100002L));
    }

    @Test
    void createUnAuth() throws Exception {
        perform(post("/rest/admin/surveyDetails/100002/questions"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createForbidden() throws Exception {
        perform(post("/rest/admin/surveyDetails/100002/questions")
                .with(userHttpBasic(user)))
                .andExpect(status().isForbidden());
    }
}