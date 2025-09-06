package com.cursordemo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger/OpenAPI configuration for the application.
 * 
 * Provides comprehensive API documentation with detailed information
 * about the Book Management API.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configure OpenAPI documentation.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Book Management API")
                        .description("A comprehensive REST API for managing books with CRUD operations and search functionality. " +
                                   "This API provides endpoints for creating, reading, updating, and deleting books, " +
                                   "as well as advanced search capabilities by title, author, and price range.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Cursor Demo Team")
                                .email("support@cursordemo.com")
                                .url("https://github.com/udayakumarreddyv/CursorDemo"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server"),
                        new Server()
                                .url("https://api.cursordemo.com")
                                .description("Production Server")
                ));
    }
}
