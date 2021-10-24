package merx.article.springframework.validation.chapter01;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class Request {
    @Size(min = 1)
    private String name;
}
