package merx.article.springframework.validation.chapter05.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import merx.article.springframework.validation.chapter05.validation.FirstName;
import merx.article.springframework.validation.chapter05.validation.LastName;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public class RequestWithFirstLastName {
    @NotNull
    @FirstName
    private String firstName;

    @NotNull
    @LastName
    private String lastName;
}
