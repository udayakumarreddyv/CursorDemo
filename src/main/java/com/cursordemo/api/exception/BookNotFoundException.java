package com.cursordemo.api.exception;

/**
 * Exception thrown when a book is not found in the system.
 * 
 * @author CursorDemo Team
 * @version 1.0.0
 */
public class BookNotFoundException extends RuntimeException {

    /**
     * Constructor with message.
     * 
     * @param message the error message
     */
    public BookNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     * 
     * @param message the error message
     * @param cause the cause of the exception
     */
    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
