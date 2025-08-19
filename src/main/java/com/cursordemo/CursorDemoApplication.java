package com.cursordemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for Cursor Demo API.
 * 
 * This application provides a REST API for managing books with CRUD operations,
 * authentication, and comprehensive documentation.
 */
@SpringBootApplication
public class CursorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CursorDemoApplication.class, args);
    }
}
