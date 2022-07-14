package app.exception;

public class EmailAlreadyInUseException extends RuntimeException
{
    public EmailAlreadyInUseException()
    {
        super("This email is already in use");
    }
}