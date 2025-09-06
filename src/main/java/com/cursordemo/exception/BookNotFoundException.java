package com.cursordemo.exception;

/**
 * Exception thrown when a book is not found in the system.
 * 
 * This exception is used to indicate that a requested book
 * does not exist in the database.
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
