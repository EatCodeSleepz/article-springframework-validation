package merx.article.springframework.validation.chapter06;

import com.fasterxml.jackson.databind.ObjectMapper;
import merx.article.springframework.validation.chapter06.dto.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ControllerTest {
    public static final String ENCODING = StandardCharsets.UTF_8.name();

    @Autowired
    WebApplicationContext webAppContext;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void init() {
        if (mockMvc == null) {
            mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                    .alwaysDo(mvcResult -> {
                        mvcResult.getRequest().setCharacterEncoding(ENCODING);
                        mvcResult.getResponse().setCharacterEncoding(ENCODING);
                    })
                    .alwaysDo(MockMvcResultHandlers.print())
                    .build();
        }
    }

    @Test
    void test() throws Exception {
        Request request = new Request();
        request.setFirstName("First 123456789 123456789 123456789 123456789 123456789");
        request.setLastName("Last?");

        /*
        Deliberately to have 2 request in 1 same test.
        Note what is printed out from LocaleResolver
        */

        // request with locale param
        mockMvc
                .perform(post(Controller.URI_POST_CHAPTER_06)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("lang", "zh")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        // request without locale param
        mockMvc
                .perform(post(Controller.URI_POST_CHAPTER_06)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }
}
