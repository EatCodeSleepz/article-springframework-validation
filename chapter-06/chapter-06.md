## Chapter 6 Custom Message & Locale

### 6.1 Custom Message Resource Location
As mentioned in Chapter 4, default validation message location is as follows.
```
resources/ValidationMessages_en.properties
resources/ValidationMessages_xx.properties
```
We can add the Country ISO code as well like
```
resources/ValidationMessages_en.properties
resources/ValidationMessages_zh.properties
resources/ValidationMessages_zh_CN.properties
resources/ValidationMessages_zh_TW.properties
```
To either change the default file name or location.
```
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public Validator getValidator() {
        return validator();
    }

    private Validator validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());

        return validator;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages/ValidationMessages");
        source.setDefaultEncoding(StandardCharsets.UTF_8.name());

        return source;
    }
}
```
Note above snippet:
* Create a `@Configuration` class, implement `WebMvcConfigurer`, override method `getValidator()`.
* In above example we `setBasename` to `messages/ValidationMessages`.
```
resources/messages/ValidationMessages_xx.properties
```
* Call `setDefaultEncoding`
* If using `JDK` prior to 9, the properties file's default encoding is `ISO-8859-1`.  
https://docs.oracle.com/javase/9/intl/internationalization-enhancements-jdk-9.htm  
Even if we write the properties file in `UTF-8`, by default it will not be read correctly.
Need to tell `Spring/JDK` to load as `UTF-8` (one way is like the code above).
* Another approach for `JDK` prior 9 is to write the message in `UTF-8 code` as follows
(taken from `hibernate-validator-7.0.1.Final.jar`, `org/hibernate/validator/ValidationMessages_zh.properties`)
```
jakarta.validation.constraints.Max.message     = \u5fc5\u987b\u5c0f\u4e8e\u6216\u7b49\u4e8e {value}
jakarta.validation.constraints.Min.message     = \u5fc5\u987b\u5927\u4e8e\u6216\u7b49\u4e8e {value}
jakarta.validation.constraints.NotNull.message = \u4e0d\u5f97\u4e3a null
jakarta.validation.constraints.Pattern.message = \u5fc5\u987b\u4e0e "{regexp}" \u5339\u914d
jakarta.validation.constraints.Size.message    = \u5927\u5c0f\u5fc5\u987b\u5728 {min} \u548c {max} \u4e4b\u95f4
```
* When using built-in constraint validators, might want to confirm if your locale is supported.
For example, try to view `hibernate-validator-7.0.1.Final.jar` (match with the version you're using).
Have a look at the directory `org/hibernate/validator/`.
See what `ValidationMessages_xx` inside.


### 6.2 Message interpolation
In the above `.properties` snippet, we see `{value}`, `{min}`, `{max}`.
Those are called `message interpolation`.
Can read more here.  
https://www.baeldung.com/spring-validation-message-interpolation


### 6.3 Set Locale
This part by no means to provide a detailed article.
But just pointed out the important points that just works.
There are also comments in the code and test case that come with this chapter.
Have a read of those comments.
If want to explore further, can search the internet.
* `Spring` uses the implementation of `org.springframework.web.servlet.LocaleResolver` interface to determine the locale.
Have a read, and note the classes that implement the interface.  
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/LocaleResolver.html
* By default, `Spring` uses this implementation `org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver`.
* `AcceptHeaderLocaleResolver` determines locale based on HTTP request header `Accept-Language`
* To make our own resolver, just implement the interface or extend one of the available classes.
This chapter comes with sample `MyLocaleResolver`.
`Spring` uses the locale returned by this method; which is one of the method from interface `LocaleResolver`.
```
Locale resolveLocale(HttpServletRequest request)
```
* If we want to change locale based on HTTP request query param, can use `LocaleChangeInterceptor`.
This one will get value from the query param with default param name `locale`.
To enable it, the usual `Spring` way (`@Configuration`, `@Bean`).
```
http://localhost:8080/my/api/getInfo?locale=en
```
* Do note though, the default locale resolver `AcceptHeaderLocaleResolver` only accepts change locale from HTTP header `Accept-Language`.
If `LocaleChangeInterceptor` is enabled; and it tries to call the `setLocale` method from `AcceptHeaderLocaleResolver`, will cause an error.
Switch to another `LocaleResolver`.
