package merx.article.springframework.validation.chapter02;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class Request {
    @NotNull
    @Email
    @Size(min = 5, max = 50)
    private String email;
}
