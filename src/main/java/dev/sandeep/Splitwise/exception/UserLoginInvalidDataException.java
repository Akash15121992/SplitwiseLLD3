package dev.sandeep.Splitwise.exception;

public class UserLoginInvalidDataException extends RuntimeException{
    public UserLoginInvalidDataException() {
    }

    public UserLoginInvalidDataException(String message) {
        super(message);
    }

    public UserLoginInvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
