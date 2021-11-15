## Chapter 2 Handling Validation Error

This chapter we're going to look at how to handle the validation error.
Instead of the default HTTP 400 response without knowing which fields cause the error.

Let's start with the request.

```
public class Request {
    @NotNull
    @Email
    @Size(min = 5, max = 50)
    private String email;
}
```

It validates an email input, must have value (not null), the length must be between 5 and 50 inclusive.

Run it via the provided test case or normally run. The test case is set to submit a value that triggers both invalid email and invalid size.

By default, if validation is not passed Spring returns HTTP status `400`. Following is shown in the test case printout.
```
MockHttpServletResponse:
           Status = 400
    Error message = null
          Headers = []
     Content type = null
             Body = 
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
```
As we can see, the above response only generally tell us that there's a validation error. But there's no info about which field, what's the error.

The constraint validation error is handled by Spring. We need to take over that part from Spring. There are lots of resources about how to override Spring error handling. One of them is following  
https://www.baeldung.com/exception-handling-for-rest-with-spring

You can choose which handling methods suit your project most. But for the validation error handling, believe a more uniform way is to handle all validation errors in one place. One of the ways of doing this, this article will use `ControllerAdvice`.

This article provides two samples of `ControllerAdvice`. Of course it is not limited to just two of these samples.
* `ControllerAdviceRest`
* `ControllerAdviceResponseEntity`

Both samples use the `InvalidValidation` object provided in this chapter to store invalid validation messages.
```
public class InvalidValidation {
    private String field;
    private String message;
}
```

### 2.1 `ControllerAdviceRest`
Have a look at the `ControllerAdviceRest` first. Remove the comment from `@RestControllerAdvice`. And run it again.

```
@RestControllerAdvice
public class ControllerAdviceRest {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected List<InvalidValidation> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<InvalidValidation> lsInvalidValidation = new ArrayList<>(ex.getBindingResult().getFieldErrorCount());

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            InvalidValidation invalidValidation = new InvalidValidation();
            invalidValidation.setField(fieldError.getField());
            invalidValidation.setMessage(fieldError.getDefaultMessage());

            lsInvalidValidation.add(invalidValidation);
        });

        return lsInvalidValidation;
    }
}
```

If we run the test case, can see the following response.
```
MockHttpServletResponse:
           Status = 400
    Error message = null
          Headers = [Content-Type:"application/json;charset=UTF-8"]
     Content type = application/json;charset=UTF-8
             Body = [{"field":"email","message":"size must be between 5 and 50"},{"field":"email","message":"must be a well-formed email address"}]
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
```
Note the response body. It shows that there's a validation error; field `email` is not well-formed and length also not correct.
In this sample, need to note the following:
1. By default Spring returns HTTP 400 for validation errors. Take a look at the sample.
```
@ResponseStatus(HttpStatus.BAD_REQUEST)
```
Since we override Spring validation handling, if we don't specify the `@ResponseStatus` it will default to HTTP 200.

2. The content type of the response in this exception handling must be the same with the `Controller` endpoint that produces it.
If different, let's say one is `application/json` and the other is `text/plain` Spring will show `org.springframework.web.HttpMediaTypeNotAcceptableException`.

### 2.2 `ControllerAdviceResponseEntity`
This has more control, can specify response type. Comment the previous sample's `@ControllerAdvice` and uncomment the one in this sample.
The response will be the same as the previous sample.
Just that by using this sample, the response can have a different content type from the `Controller` that produces the validation error.
```
@ControllerAdvice
public class ControllerAdviceResponseEntity {
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<List<InvalidValidation>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<InvalidValidation> lsInvalidValidation = new ArrayList<>(ex.getBindingResult().getFieldErrorCount());

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            InvalidValidation invalidValidation = new InvalidValidation();
            invalidValidation.setField(fieldError.getField());
            invalidValidation.setMessage(fieldError.getDefaultMessage());

            lsInvalidValidation.add(invalidValidation);
        });

        return ResponseEntity
                .badRequest()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(lsInvalidValidation);
    }
}
```

Aside of the two above, the `ControllerAdvice` class also can extend `org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler` and override the method `handleMethodArgumentNotValid`.

That's all about handling validation errors. Next chapter we'll look at how to write our own custom validator.