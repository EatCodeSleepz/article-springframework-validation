package merx.article.springframework.validation.chapter03;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Request {
    @NotNull
    @IPv4
    private String ip;
}
