## Chapter 5 Combining Validation Annotations

### 5.1 Combine
Let's say we have this use case; to validate `first name` and `last name`.
Both have the same validation rules, between 1 and 50 characters inclusive, no punctuations.
We can create a constraint annotation called `@Name`.

```
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
```
Note above snippet:
* The `@Name` combines 2 constraint annotations: `@Size` and `@Pattern`.
* Adding `@ReportAsSingleViolation`.
If any constraint annotation validation fails, the validation message will be taken from `@Name`'s message `{merx.article.springframework.validation.Name.message}`.
And also will be returned as 1 violation (well, as the name suggests `@ReportAsSingleViolation`).
* Without `@ReportAsSingleViolation`.
If any constraint annotation validation fails, the validation message will be the message from the annotation that resulted validation failed.
As we've seen in the previous chapter, if 1 field hits multiple failed validation, multiple messages will be returned for that 1 field.

#### 5.1.1 Implementation A
```
public class RequestWithName {
    @NotNull
    @Name(message = "{merx.article.springframework.validation.FirstName.message}")
    private String firstName;

    @NotNull
    @Name(message = "{merx.article.springframework.validation.LastName.message}")
    private String lastName;
}
```
Note about above snippet:
* Since `first name` and `last name` have the same validation rule, we can use the same `@Name` validation with different messages.
* If we don't put `@ReportAsSingleViolation` at the previous section, the message we specify here will not be shown;
as it will return the annotation that validates fail (`@Size` or `@Pattern`). And `@Name` itself does not have it's own validator (`validatedBy` is empty)
* If want to use `@Name` and specify the message on the field, suggest putting the message key as constant.

#### 5.1.2 Implementation B
Can even go further by creating `@FirstName` and `@LastName`, and specify the default message.
```
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
```
```
@Name

@Documented
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER })
public @interface LastName {
    String message() default "{merx.article.springframework.validation.LastName.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```
Then in the field, we just put the `@FirstName` and `@LastName` without needing to care about the message.
```
public class RequestWithFirstLastName {
    @NotNull
    @FirstName
    private String firstName;

    @NotNull
    @LastName
    private String lastName;
}
```

### 5.2 Suggestion
* Create this kind of combined constraint validators for fields.
Most of them are just combined annotations, not really needed to code the `validatedBy` validator.
Ex: `@Address`, `@Password`, `@PostalCode` etc.
* If the field is mandatory, add `@NotNull`. Built-in validators treats null as valid, this works well with `@NotNull` as mandatory;
thus, our own validator code is recommended to do the same unless there is a specific reason.
* Multiple endpoints that accept the same input type, just use the created annotations.
* This way, all the validation rules are centralized, not scattered all over the request object fields.
* If there's a validation rule change, just need to change that 1 annotation.
