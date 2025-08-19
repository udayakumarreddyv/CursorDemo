package com.cursordemo.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI configuration for Swagger documentation.
 * 
 * This class configures the OpenAPI specification for the Book Management API,
 * including API information, security schemes, and server details.
 * 
 * @author CursorDemo Team
 * @version 1.0.0
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configure the OpenAPI specification.
     * 
     * @return the OpenAPI configuration
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CursorDemo Book Management API")
                        .description("A comprehensive REST API for managing books with CRUD operations, " +
                                   "search functionality, and price-based filtering. This API provides " +
                                   "secure access to book management features with Basic Authentication.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("CursorDemo Team")
                                .email("team@cursordemo.com")
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
                                .description("Production Server")))
                .addSecurityItem(new SecurityRequirement().addList("BasicAuth"))
                .components(new Components()
                        .addSecuritySchemes("BasicAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")
                                .description("Basic Authentication using username and password")));
    }
}
