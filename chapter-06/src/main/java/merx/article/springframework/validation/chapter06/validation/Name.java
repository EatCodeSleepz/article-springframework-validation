package merx.article.springframework.validation.chapter06.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.*;

@Size(min = 1, max = 50)
@Pattern(regexp = "[^\\p{Punct}]*") // no punctuation
@ReportAsSingleViolation

@Documented
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER })
public @interface Name {
    String message() default "{merx.article.springframework.validation.Name.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
