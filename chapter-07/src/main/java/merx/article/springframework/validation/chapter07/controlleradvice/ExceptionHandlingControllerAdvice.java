package merx.article.springframework.validation.chapter07.controlleradvice;

import merx.article.springframework.validation.chapter07.InvalidValidation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
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

    @ExceptionHandler(value = { BindException.class })
    protected ResponseEntity<List<InvalidValidation>> handleBind(BindException ex) {
        return ResponseEntity
                .badRequest()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(buildInvalidValidationInfo(ex.getFieldErrors()));
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
