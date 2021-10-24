## Chapter 1 Getting Started
### 1.1 Controller
```
public class Controller {
    public static final String URI_POST_CHAPTER_01 = "/chapter01";

    @PostMapping(
            value = URI_POST_CHAPTER_01,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public String sample01(
            @Valid @RequestBody Request request) {

        return String.format("It is a valid request %s", Instant.now());
    }
}
```

The `@Valid` annotation is to tell the validation framework to validate the attributes inside the `Request` as well.  

For example, if we just put it as follows. The validation framework will only validate that `Request` is not null. It will not go inside the `Request` and validate the attributes inside.
```
@NotNull Request request
```

### 1.2 Request object

```
public class Request {
    @Size(min = 1)
    private String name;
}
```

The `Request` object has an attribute called `name`; With minimal size of 1 `@Size(min = 1)`.
If in the above Controller we don't put `@Valid`, this `name` attribute will not get validated.

### 1.3 Run it
Run it via provided test case or normally run. Play around with the value of `@Size` or the value submitted.
By default, if validation not passed Spring returns HTTP status `400`

### 1.4 Built-in validator constraints
Below are two links listed the built-in constraints.
Note that in early of this article, mentioned Bean Validation is just a specification. Need some implementation to do the actual validation.

Please check the Java and Hibernate each link conform to which JSR spec. It does not necessarily mean that they refer to the same JSR. But if we include `spring-boot-starter-validation` in the project, the version has been taken care of.

Java  
https://docs.oracle.com/javaee/7/api/javax/validation/constraints/package-summary.html

Hibernate Validator (the reference implementation)  
https://docs.jboss.org/hibernate/validator/7.0/reference/en-US/html_single/#section-builtin-constraints

Next chapter we'll look at handling the validation error.
