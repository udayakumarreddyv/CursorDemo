package com.cursordemo.config;

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
 * This class configures the OpenAPI specification for the REST API
 * including API information, security schemes, and server details.
 * 
 * @author Cursor Demo Team
 * @version 1.0.0
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configure OpenAPI specification.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cursor Demo Book API")
                        .description("A comprehensive REST API for managing books with CRUD operations and search capabilities.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Cursor Demo Team")
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
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .components(new Components()
                        .addSecuritySchemes("basicAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")
                                .description("Basic Authentication")));
    }
}
