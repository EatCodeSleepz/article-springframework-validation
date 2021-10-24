package merx.article.springframework.validation.chapter04;

import com.fasterxml.jackson.databind.ObjectMapper;
import merx.article.springframework.validation.chapter04.dto.Request;
import merx.article.springframework.validation.chapter04.dto.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
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
        request.setIp("0.0.0.256");

        UserDetail userDetail = new UserDetail();
        userDetail.setMemberId("AB123456");
        userDetail.setName("");
        userDetail.setEmail("em");
        request.setUserDetail(userDetail);

        mockMvc
                .perform(post(Controller.URI_POST_CHAPTER_04)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "en")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}
