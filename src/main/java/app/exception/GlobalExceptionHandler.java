package app.exception;

import app.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(value={EmailAlreadyInUseException.class,UsernameAlreadyInUseException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse alreadyInUseExceptionHandler(Exception e)
    {
        return new ExceptionResponse(e.getMessage());
    }
}