package merx.article.springframework.validation.chapter03;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<List<InvalidValidation>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .badRequest()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(buildInvalidValidationInfo(ex.getBindingResult().getFieldErrors()));
    }

    List<InvalidValidation> buildInvalidValidationInfo(List<FieldError> fieldErrors) {
        List<InvalidValidation> lsInvalidValidation = new ArrayList<>(fieldErrors.size());

        for (FieldError fieldError : fieldErrors) {
            InvalidValidation invalidValidation = new InvalidValidation();
            invalidValidation.setField(fieldError.getField());
            invalidValidation.setMessage(fieldError.getDefaultMessage());

            lsInvalidValidation.add(invalidValidation);
        }

        return lsInvalidValidation;
    }
}
