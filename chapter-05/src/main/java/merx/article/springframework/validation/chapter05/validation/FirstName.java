package merx.article.springframework.validation.chapter05.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Name

@Documented
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER })
public @interface FirstName {
    String message() default "{merx.article.springframework.validation.FirstName.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
