package merx.article.springframework.validation.chapter07;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
public class Request {
    @Size(min = 1, max = 50)
    private String address;

    @JsonDeserialize(using = EmptyStringToNullDeserializer.class)
    @Size(min = 1, max = 50)
    private String city;
}
