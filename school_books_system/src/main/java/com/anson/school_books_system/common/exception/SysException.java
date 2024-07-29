package com.anson.school_books_system.common.exception;



public class SysException extends RuntimeException {
    public SysException() {
    }

    public SysException(String message, Object... args) {
        super(String.format(message, args));
    }


    public SysException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }

    public SysException(Throwable cause) {
        super(cause);
    }

}
