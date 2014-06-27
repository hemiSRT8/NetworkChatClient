package ua.khvorov.exception;


public class ServerOfflineException extends RuntimeException {
    public ServerOfflineException(String message, Throwable cause) {
        super(message, cause);
    }
}
