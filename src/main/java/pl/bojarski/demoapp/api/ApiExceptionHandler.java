package pl.bojarski.demoapp.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.bojarski.demoapp.exceptions.ProductIdIncorrectException;
import pl.bojarski.demoapp.exceptions.ProductNotFoundException;
import pl.bojarski.demoapp.exceptions.ProductRequestNotValidException;

import static org.springframework.http.HttpStatus.*;

@SuppressWarnings("unchecked")
@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity handle(ProductNotFoundException exception) {
        return new ResponseEntity(exception.getMessage(), NOT_FOUND);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ProductRequestNotValidException.class)
    public ResponseEntity handleProductRequestNotValid(ProductRequestNotValidException exception) {
        return new ResponseEntity(exception.getMessage(), UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ProductIdIncorrectException.class)
    public ResponseEntity handleProductIdIncorrect(ProductIdIncorrectException exception) {
        return new ResponseEntity(exception.getMessage(), BAD_REQUEST);
    }
}
