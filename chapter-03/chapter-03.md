## Chapter 3 Creating Custom Validation

Let's try to build our own custom validator; to validate whether an input String is a valid IPv4.
A valid IPv4 should be `n1.n2.n3.n4` where the value of each `n` should be `0 <= n <= 255`.

Below writing only shows partial codes.
The whole Java file is available in this chapter sample code `IPv4.java`, `IPv4Validator.java`.
So please refer to the whole Java code if find the partial codes not clear enough.

### 3.1 The Annotation
```
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
```
Those lines at the end that have the comment 'validation mandatory' are meant for... well, validation.
Other lines are just normal Java annotations. For how to create Java annotation, can search for the resources on the internet.

To note:
1. The `@Constraint` to indicate this annotation is for constraint validation.
Here we also specify the `validatedBy` (mandatory) and fill that with the value of our custom validator class.
In a later chapter, we'll look at some use cases that do not need to write our own validator class.
Thus `validatedBy` could be empty.
```
@Constraint(validatedBy = { IPv4Validator.class }) /* validation mandatory */
```
2. In the body, these are mandatory for validation.
```
String message() default ""; /* validation mandatory */
Class<?>[] groups() default {}; /* validation mandatory */
Class<? extends Payload>[] payload() default {}; /* validation mandatory */
```
3. The `message()`
```
String message() default "Invalid IPv4";
```
Next chapter we'll look at various ways to specify validation messages. Also about i18n.

4. This `allowReservedAddress()` is how we pass parameters to the validator.
```
@IPv4(allowReservedAddress = false)
private String ip;
```


### 3.2 The Validator
Following is the skeleton.
```
public class IPv4Validator implements ConstraintValidator<IPv4, String> {
    private boolean allowReservedAddress;

    @Override
    public void initialize(IPv4 constraintAnnotation) {
        allowReservedAddress = constraintAnnotation.allowReservedAddress();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
```

1. The validator class implements `javax.validation.ConstraintValidator`.
Inside the diamond:
* `IPv4` refers to the annotation
* `String` is the type of the value that will be passed to this validator to get validated.
```
ConstraintValidator<IPv4, String>
```
2. This is how to accept parameters from annotations in validator. If the validator does not have parameters, no need to `@Override` this method.
```
private boolean allowReservedAddress;

@Override
public void initialize(IPv4 constraintAnnotation) {
    allowReservedAddress = constraintAnnotation.allowReservedAddress();
}
```
3. This is the actual validation logic.
```
@Override
public boolean isValid(String value, ConstraintValidatorContext context) {
    return false;
}
```
To note here:
* The `value` passed to this method could be `null`. If there's no special need, it is recommended to return `true` if the value is `null`.
By doing so, the validator can be used as a 'non mandatory' validator. Take a look at the following.
```
@NotNull
@IPv4
private String ip;
```
If the field is mandatory add `@NotNull`; otherwise just use `@IPv4` and make the field not mandatory.
* The type of the `value` is `String`; same type as what mentioned in point 1 above. 

Have a look at the `IPv4Validator.java` for the implementation of the logic.
Same as previous chapters, this also comes with a test case.
Can either actually run the app or run the test case to see the result.

Well, that's about it. Next chapter we'll look at various ways to specify the validation message.
