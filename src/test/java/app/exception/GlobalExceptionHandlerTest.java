package app.exception;

import app.response.ExceptionResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest
{
    @Test
    void alreadyInUseExceptionHandler()
    {
        GlobalExceptionHandler globalExceptionHandler=new GlobalExceptionHandler();
        EmailAlreadyInUseException emailAlreadyInUseException=new EmailAlreadyInUseException();
        ExceptionResponse exceptionResponse=globalExceptionHandler.alreadyInUseExceptionHandler(emailAlreadyInUseException);
        assertEquals(emailAlreadyInUseException.getMessage(),exceptionResponse.getException());
    }
}