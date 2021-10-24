package merx.article.springframework.validation.chapter02;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    void test01() throws Exception {
        Request request = new Request();
        request.setEmail("123");

        mockMvc
                .perform(post(Controller.URI_POST_CHAPTER_02)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}
