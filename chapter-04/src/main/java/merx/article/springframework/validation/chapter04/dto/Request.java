package merx.article.springframework.validation.chapter04.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import merx.article.springframework.validation.chapter04.validation.IPv4;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public class Request {
    @NotNull
    @Valid
    private UserDetail userDetail;

    @NotNull
    @IPv4
    private String ip;
}
