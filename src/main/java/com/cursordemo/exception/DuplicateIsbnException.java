package com.cursordemo.exception;

/**
 * Exception thrown when attempting to create a book with an ISBN that already exists.
 * 
 * This exception is used when trying to create or update a book with an ISBN
 * that is already associated with another book in the system.
 * 
 * @author Cursor Demo Team
 * @version 1.0.0
 */
public class DuplicateIsbnException extends RuntimeException {

    /**
     * Constructs a new DuplicateIsbnException with the specified detail message.
     * 
     * @param message the detail message
     */
    public DuplicateIsbnException(String message) {
        super(message);
    }

    /**
     * Constructs a new DuplicateIsbnException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause
     */
    public DuplicateIsbnException(String message, Throwable cause) {
        super(message, cause);
    }
}
