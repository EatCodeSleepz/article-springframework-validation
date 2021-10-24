package merx.article.springframework.validation.chapter06;

import merx.article.springframework.validation.chapter06.dto.Request;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class Controller {
    public static final String URI_POST_CHAPTER_06 = "/chapter06";

    @PostMapping(
            value = URI_POST_CHAPTER_06,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public String sampleWithName(
            @Valid @RequestBody Request request) {

        return String.format("It is a valid request (with name) %s", Instant.now());
    }
}
