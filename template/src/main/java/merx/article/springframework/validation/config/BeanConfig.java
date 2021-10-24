package merx.article.springframework.validation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import merx.article.springframework.validation.deserializer.EmptyStringToNullDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
