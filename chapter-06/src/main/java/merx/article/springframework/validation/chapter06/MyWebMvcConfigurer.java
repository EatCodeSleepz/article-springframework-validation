package merx.article.springframework.validation.chapter06;

import merx.article.springframework.validation.chapter06.MyLocaleResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.nio.charset.StandardCharsets;

@Configuration
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

    @Bean
    //@RequestScope
    public MyLocaleResolver localeResolver() {
        System.out.println("xXx localeResolver");
        return new MyLocaleResolver();
    }



    /*
    Below 2 methods are not required if no plan to change locale via HTTP query string param
    */

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        System.out.println("xXx localeChangeInterceptor");
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();

        // the default value is LocaleChangeInterceptor.DEFAULT_PARAM_NAME, 'locale'
        localeChangeInterceptor.setParamName("lang");

        // note, the param is still in query string, even it is POST
        // or just don't call setHttpMethods if want for all HTTP methods
        localeChangeInterceptor.setHttpMethods("GET", "POST");

        return localeChangeInterceptor;
    }
}
