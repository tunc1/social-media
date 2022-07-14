package app.response;

public class ExceptionResponse
{
    private String exception;
    public ExceptionResponse(String exception)
    {
        this.exception=exception;
    }
    public String getException()
    {
        return exception;
    }
}