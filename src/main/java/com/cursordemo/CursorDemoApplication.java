package com.cursordemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for Cursor Demo REST API.
 * 
 * This application provides a complete REST API for managing books
 * with CRUD operations, search capabilities, and authentication.
 * 
 * @author Cursor Demo Team
 * @version 1.0.0
 */
@SpringBootApplication
public class CursorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CursorDemoApplication.class, args);
    }
}
