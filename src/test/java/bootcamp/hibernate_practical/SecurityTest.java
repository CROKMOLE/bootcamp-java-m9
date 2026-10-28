package bootcamp.hibernate_practical;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTests {
    @Autowired
    MockMvc mvc;

    private static String validJson = """
            {
              "title": "Some Title",
              "author": "Some person",
              "genre": "Some genre",
              "publicationYear": 2007,
              "available": true
            }
            """;

    @Test
    void unauthenticated_is401() throws Exception {
        mvc.perform(get("/api/books")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void userCanRead() throws Exception {
        mvc.perform(get("/api/books").
                with(httpBasic("user", "user")))
                .andExpect(status().isOk());
    }

    @Test
    void userCannotWrite() throws Exception {
        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content("{}")
                        .with(httpBasic("user", "user"))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanWrite() throws Exception {
        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content(validJson)
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isCreated());
    }

    @Test
    void missingCsrf_isRejected() throws Exception {
        mvc.perform(post("/login").param("username", "user").param("password", "user123"))
                .andExpect(status().isForbidden());
    }
}