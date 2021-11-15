## 7 Non Mandatory String Field - Null or Empty String

Suggested that in our custom validator if the value is `null`, to return true (valid) from the `isValid` method.
That is the same behaviour as built-in validators;
in-line if creating custom validation annotations that utilize/combine built-in validators.
* So when the field is mandatory, just need to add `@NotNull`
```
@NotNull
@Size(min = 1, max = 50)
private String address;
```
* If not mandatory, no need `@NotNull`
```
@Size(min = 1, max = 50)
private String address;
```
* Then from backend perspective, if no value just passes `null`.
* But from the frontend perspective,
  sometimes the components being used, if the user fills nothing, it is an empty string.
  Thus pass empty string to backend and get validated.

The solution below converts `empty string` to `null` on the backend side.
_Do take note if those values have special usage in your project_.

### 7.1 Request in JSON
This 7.1 is only for requests that come in the form of a JSON body.
As of this writing, `Spring` uses `Jackson` as default JSON processor.

1. Create the `deserializer`.
```
public class EmptyStringToNullDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jsonParser.readValueAsTree();
        String str = jsonNode.asText();

        return "".equals(str) ? null : str;
    }
}
```

2. Do this if want to apply it globally across all requests.
```
@Configuration
public class BeanConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(String.class, new EmptyStringToNullDeserializer());
        objectMapper.registerModule(simpleModule);

        return objectMapper;
    }
}
```

3. If want to do it just for certain field
```
@JsonDeserialize(using = EmptyStringToNullDeserializer.class)
@Size(min = 1, max = 50)
private String city;
```

### 7.2 Request in Form Data
This 7.2 is for requests that come in as form submission (`multipart/form-data`, `application/x-www-form-urlencoded`).

1. Do this if want to handle it globally across all requests.
```
@ControllerAdvice
public class BindingControllerAdvice {
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
```

2. To handle it at the controller level, add this in `Controller`.
```
@InitBinder
public void initBinder(WebDataBinder webDataBinder) {
    webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
}
```

Note that the method `registerCustomEditor` has some overload.
Can specify the field name affected by this rule if want to.

3. If there are validation violations, the exception thrown is `org.springframework.validation.BindException`
instead of `org.springframework.web.bind.MethodArgumentNotValidException`.
Have a look at the `ExceptionHandlingControllerAdvice` that comes with this chapter.
