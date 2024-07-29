package com.anson.school_books_system.common.exception;



public class AuthException extends RuntimeException {
    public AuthException() {
    }

    public AuthException(String message, Object... args) {
        super(String.format(message, args));
    }


    public AuthException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

}
