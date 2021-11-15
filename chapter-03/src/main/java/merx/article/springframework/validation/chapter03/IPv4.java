package merx.article.springframework.validation.chapter03;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { IPv4Validator.class }) /* validation mandatory */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER })
public @interface IPv4 {
    String message() default "Invalid IPv4"; /* validation mandatory */
    Class<?>[] groups() default {}; /* validation mandatory */
    Class<? extends Payload>[] payload() default {}; /* validation mandatory */

    boolean allowReservedAddress() default true;
}
