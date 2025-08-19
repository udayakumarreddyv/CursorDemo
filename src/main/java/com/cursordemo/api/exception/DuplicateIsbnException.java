package com.cursordemo.api.exception;

/**
 * Exception thrown when attempting to create a book with an ISBN that already exists.
 * 
 * @author CursorDemo Team
 * @version 1.0.0
 */
public class DuplicateIsbnException extends RuntimeException {

    /**
     * Constructor with message.
     * 
     * @param message the error message
     */
    public DuplicateIsbnException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     * 
     * @param message the error message
     * @param cause the cause of the exception
     */
    public DuplicateIsbnException(String message, Throwable cause) {
        super(message, cause);
    }
}
