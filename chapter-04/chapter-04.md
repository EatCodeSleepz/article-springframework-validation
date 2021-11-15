## Chapter 4 Validation Message

### 4.1 Specify the Message

We can specify the validation message either with:
1. Message key such as
`{my.project.package.validation.Mandatory.message}`;
note it is enclosed by curly brackets.
This is a better practice and in case the project needs `i18n`.
3. Hardcode.

Following we'll see various ways to specify the message.

#### 4.1.1 Nothing
```
@NotBlank
private String comment;
```
With built-in validator, there's a default message associated with it.

#### 4.1.2 At the annotated field itself
```
@NotNull
@Pattern(
        regexp = "^[a-z]{2}[0-9]{6}$", flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "{merx.article.springframework.validation.MemberId.message}")
private String memberId;
```
For certain built-in validators like `@Pattern`, it is better to specify their own message.
The default message of the validator will show the `regexp` to the users, which can be confusing.

#### 4.1.3 At the custom validator
```
public @interface IPv4 {
    String message() default "{merx.article.springframework.validation.IPv4.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    boolean allowReservedAddress() default true;
}
```
Remember our custom IPv4 validator? :)

### 4.2 Internationalization (i18n)

The validation message default location.
```
resources/ValidationMessages_en.properties
resources/ValidationMessages_xx.properties
```

The content
```
my.project.package.validation.IPv4.message=IP is invalid
my.project.package.validation.MemberId.message=Member ID is invalid; 2 alphabets followed by 6 digits
```

By default, Spring determines the `locale` based on the request HTTP header `Accept-Language`.
If the locale is not found, Spring will fall back to default locale.

### 4.3 Run It
As usual, can play around with the provided test case or deploy and try.

The model
```
public class Request {
    @NotNull
    @Valid
    private UserDetail userDetail;

    @NotNull
    @IPv4
    private String ip;
}
```
```
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
    private String email; // can see there's no @NotNull here. this field is not mandatory
}
```
Instead of a flat structure, the sample in this chapter has class member `UserDetail`.
Still remember about the `@Valid` mentioned in `Chapter 1`?

Request JSON  
Let's say we have the following request JSON.
```
{
    "userDetail": {
        "memberId": "AB123456",
        "name": "",
        "email": "em"
    },
    "ip": "0.0.0.256"
}
```
Response JSON
```
[
    {
        "field": "ip",
        "message": "IP is invalid"
    },
    {
        "field": "userDetail.email",
        "message": "must be a well-formed email address"
    },
    {
        "field": "userDetail.email",
        "message": "size must be between 3 and 100"
    },
    {
        "field": "userDetail.name",
        "message": "size must be between 1 and 50"
    }
]
```
Note:
* The value of `field` (or name it as you like) is the JSON structure that violates the validation rule.
* It is possible for 1 field to hit more than 1 validation violation.
Can see the `userDetail.email` hits both `@Size` and `@Email`.
In the next chapter we'll look at how to combine to one message should we choose to.
* Need to decide whether to put the field name in the message itself.
As in `size must be between 3 and 100` or `First name size must be between 1 and 50`.
* The default message from built-in validators does not have the field name inside; as those are generic validators.

Change the request value and note what's the response.
When ready, let's head to the next chapter; about combining validators and messages.
