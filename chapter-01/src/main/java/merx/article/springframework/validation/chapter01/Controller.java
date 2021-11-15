package merx.article.springframework.validation.chapter01;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;

@RestController
@RequestMapping(
        produces = MediaType.TEXT_PLAIN_VALUE
)
public class Controller {
    public static final String URI_POST_CHAPTER_01 = "/chapter01";

    @PostMapping(
            value = URI_POST_CHAPTER_01,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public String sample01(
            @Valid @RequestBody Request request) {

        return String.format("It is a valid request %s", Instant.now());
    }
}
