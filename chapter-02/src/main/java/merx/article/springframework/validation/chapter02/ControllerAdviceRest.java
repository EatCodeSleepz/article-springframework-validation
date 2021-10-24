package merx.article.springframework.validation.chapter02;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

//@RestControllerAdvice
public class ControllerAdviceRest {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected List<InvalidValidation> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<InvalidValidation> lsInvalidValidation = new ArrayList<>(ex.getBindingResult().getFieldErrorCount());

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            InvalidValidation invalidValidation = new InvalidValidation();
            invalidValidation.setField(fieldError.getField());
            invalidValidation.setMessage(fieldError.getDefaultMessage());

            lsInvalidValidation.add(invalidValidation);
        });

        return lsInvalidValidation;
    }
}
