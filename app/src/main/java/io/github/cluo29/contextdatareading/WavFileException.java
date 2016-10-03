package io.github.cluo29.contextdatareading;

/**
 * Created by Comet on 03/10/16.
 */
public class WavFileException extends Exception
{
    public WavFileException()
    {
        super();
    }

    public WavFileException(String message)
    {
        super(message);
    }

    public WavFileException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public WavFileException(Throwable cause)
    {
        super(cause);
    }
}
