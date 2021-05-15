package ru.usersSurvey.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.usersSurvey.model.Role;
import ru.usersSurvey.model.SurveyDetails;
import ru.usersSurvey.model.User;
import ru.usersSurvey.repository.SurveyDetailsRepository;
import ru.usersSurvey.web.json.JacksonObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminSurveyDetailsControllerTest extends AbstractControllerTest {

    @Autowired
    private JacksonObjectMapper objectMapper;

    @Autowired
    private SurveyDetailsRepository repository;

    private final User admin = new User("admin", "admin", Role.USER, Role.ADMIN);
    private final User user = new User("user", "user", Role.USER);

    @Test
    void create() throws Exception {
        SurveyDetails newSurvey = new SurveyDetails("Новый опрос", null);
        String created = perform(post("/rest/admin/surveyDetails")
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newSurvey)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        assertEquals(created, "{\"id\":100010,\"description\":\"Новый опрос\"}");
    }

    @Test
    void update() throws Exception {
        SurveyDetails updated = new SurveyDetails(100002, "Новое Описание", null);
        perform(put("/rest/admin/surveyDetails/100002")
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertEquals("Новое Описание", repository.findById(100002).getDescription());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete("/rest/admin/surveyDetails/100002")
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertNull(repository.findById(100002));
    }

    @Test
    void createUnAuth() throws Exception {
        perform(post("/rest/admin/surveyDetails"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createForbidden() throws Exception {
        perform(post("/rest/admin/surveyDetails")
                .with(userHttpBasic(user)))
                .andExpect(status().isForbidden());
    }
}