package com.cursordemo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for CursorDemo API.
 * 
 * This application provides a REST API for book management with the following features:
 * - CRUD operations for books
 * - Search functionality
 * - Basic authentication
 * - H2 in-memory database
 * - Swagger/OpenAPI documentation
 * 
 * @author CursorDemo Team
 * @version 1.0.0
 */
@SpringBootApplication
public class CursorDemoApplication {

    /**
     * Main method to start the Spring Boot application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CursorDemoApplication.class, args);
    }
}
