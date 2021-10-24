package merx.article.springframework.validation.chapter04;

import merx.article.springframework.validation.chapter04.dto.Request;
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
    public static final String URI_POST_CHAPTER_04 = "/chapter04";

    @PostMapping(
            value = URI_POST_CHAPTER_04,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public String sample01(
            @Valid @RequestBody Request request) {

        return String.format("It is a valid request %s", Instant.now());
    }
}
