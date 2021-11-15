package merx.article.springframework.validation.chapter05.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import merx.article.springframework.validation.chapter05.validation.Name;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public class RequestWithName {
    @NotNull
    @Name(message = "{merx.article.springframework.validation.FirstName.message}")
    private String firstName;

    @NotNull
    @Name(message = "{merx.article.springframework.validation.LastName.message}")
    private String lastName;
}
