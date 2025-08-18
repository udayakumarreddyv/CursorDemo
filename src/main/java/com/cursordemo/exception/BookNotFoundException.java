package com.cursordemo.exception;

/**
 * Exception thrown when a book is not found.
 * 
 * This exception is used when attempting to retrieve, update, or delete
 * a book that does not exist in the system.
 * 
 * @author Cursor Demo Team
 * @version 1.0.0
 */
public class BookNotFoundException extends RuntimeException {

    /**
     * Constructs a new BookNotFoundException with the specified detail message.
     * 
     * @param message the detail message
     */
    public BookNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new BookNotFoundException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause
     */
    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
