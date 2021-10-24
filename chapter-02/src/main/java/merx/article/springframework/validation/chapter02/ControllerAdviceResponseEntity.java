package merx.article.springframework.validation.chapter02;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

//@ControllerAdvice
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
