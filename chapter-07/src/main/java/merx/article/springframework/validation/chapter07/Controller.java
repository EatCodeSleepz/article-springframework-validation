package merx.article.springframework.validation.chapter07;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class Controller {
    public static final String URI_POST_CHAPTER_07_JSON = "/chapter07/json";
    public static final String URI_POST_CHAPTER_07_FORM_DATA = "/chapter07/formdata";

    @PostMapping(
            value = URI_POST_CHAPTER_07_JSON,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public String sampleJson(
            @Valid @RequestBody Request request) {

        return String.format("It is a valid request %s", Instant.now());
    }

    @PostMapping(
            value = URI_POST_CHAPTER_07_FORM_DATA,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String sampleFormData(
            @Valid @ModelAttribute Request request) {

        return String.format("It is a valid request %s", Instant.now());
    }
}
