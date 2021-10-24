package merx.article.springframework.validation.chapter06;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    public static final String ATTR_LOCALE = "locale";

    private Locale defaultLocale;

    public static final Locale DEFAULT_LOCALE = new Locale(
            System.getProperty("user.language"),
            System.getProperty("user.country")
    );

    /*
    The System.out.printf below is intended.
    */

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = (Locale) request.getAttribute(ATTR_LOCALE);

        /*
        Print out the request to show that for 'same' request,
        setLocale is called before resolveLocale; but it is same request.
        */
        System.out.printf(
                "xXx resolveLocale [request=%s] [attr-locale=%s] [this-default-locale=%s]%n",
                request, locale, this.defaultLocale);

        return locale != null ? locale : DEFAULT_LOCALE;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        System.out.printf("xXx setLocale [request=%s] [response=%s] [locale=%s]%n",
                request, response, locale);

        request.setAttribute(ATTR_LOCALE, locale);

        /*
        If plan to save to member as below, REMEMBER Spring default scope is Singleton.
        Thus every requests coming in will share this same default locale.
        Consider set the scope of this to Request scope.
        */
        this.defaultLocale = locale;
    }
}
