package merx.article.springframework.validation.chapter04.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
public class UserDetail {
    @NotNull
    @Pattern(
            regexp = "^[a-z]{2}[0-9]{6}$", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "{merx.article.springframework.validation.MemberId.message}")
    private String memberId;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @Email
    @Size(min = 3, max = 100)
    private String email;
}
