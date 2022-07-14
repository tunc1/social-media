package app.exception;

public class UsernameAlreadyInUseException extends RuntimeException
{
    public UsernameAlreadyInUseException()
    {
        super("This username is already in use");
    }
}