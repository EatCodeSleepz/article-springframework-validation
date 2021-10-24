package merx.article.springframework.validation.chapter05;

import merx.article.springframework.validation.chapter05.dto.RequestWithFirstLastName;
import merx.article.springframework.validation.chapter05.dto.RequestWithName;
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
    public static final String URI_POST_CHAPTER_05_NAME = "/chapter05/name";
    public static final String URI_POST_CHAPTER_05_FIRST_LAST_NAME = "/chapter05/firstLastName";

    @PostMapping(
            value = URI_POST_CHAPTER_05_NAME,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public String sampleWithName(
            @Valid @RequestBody RequestWithName request) {

        return String.format("It is a valid request (with name) %s", Instant.now());
    }

    @PostMapping(
            value = URI_POST_CHAPTER_05_FIRST_LAST_NAME,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public String sampleWithFirstLastName(
            @Valid @RequestBody RequestWithFirstLastName request) {

        return String.format("It is a valid request (with first last name) %s", Instant.now());
    }

}
