# Spring Boot REST API Development Rules

## Architecture Style
- **Layered Architecture**: Controller → Service → Repository → Entity
- **Separation of Concerns**: Each layer has a specific responsibility
- **DTO Pattern**: Use DTOs for API requests/responses, Entities for database operations
- **Clean Architecture**: Dependencies flow inward (Controller → Service → Repository)

## Technology Stack
- **Java Version**: 17 (LTS)
- **Spring Boot Version**: 3.2.x (latest stable)
- **Database**: H2 (in-memory for development)
- **Build Tool**: Maven
- **Authentication**: Spring Security with Basic Auth
- **Documentation**: OpenAPI 3 (Swagger)
- **Testing**: JUnit 5, Testcontainers for integration tests

## Naming Conventions

### Classes
- **Controllers**: `*Controller` (e.g., `BookController`)
- **Services**: `*Service` (e.g., `BookService`)
- **Repositories**: `*Repository` (e.g., `BookRepository`)
- **Entities**: `*` (e.g., `Book`)
- **DTOs**: `*Request`, `*Response`, `*DTO` (e.g., `BookRequest`, `BookResponse`)
- **Exceptions**: `*Exception` (e.g., `BookNotFoundException`)

### Methods
- **Controllers**: HTTP verb + resource (e.g., `getBook`, `createBook`, `updateBook`, `deleteBook`)
- **Services**: Business logic verbs (e.g., `findById`, `save`, `update`, `delete`)
- **Repositories**: JPA method names (e.g., `findByTitle`, `findByAuthor`)

### Variables
- **Camel Case**: `bookTitle`, `authorName`
- **Constants**: `UPPER_SNAKE_CASE`
- **Package names**: `lowercase.with.dots`

## Folder Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── cursordemo/
│   │           ├── CursorDemoApplication.java
│   │           ├── controller/
│   │           │   └── BookController.java
│   │           ├── service/
│   │           │   ├── BookService.java
│   │           │   └── impl/
│   │           │       └── BookServiceImpl.java
│   │           ├── repository/
│   │           │   └── BookRepository.java
│   │           ├── entity/
│   │           │   └── Book.java
│   │           ├── dto/
│   │           │   ├── BookRequest.java
│   │           │   └── BookResponse.java
│   │           ├── exception/
│   │           │   ├── GlobalExceptionHandler.java
│   │           │   ├── BookNotFoundException.java
│   │           │   └── ValidationException.java
│   │           └── config/
│   │               ├── SecurityConfig.java
│   │               └── OpenApiConfig.java
│   └── resources/
│       ├── application.yml
│       ├── application-dev.yml
│       └── data.sql
└── test/
    └── java/
        └── com/
            └── cursordemo/
                ├── controller/
                ├── service/
                └── repository/
```

## Dependency Management Rules
- Use Maven for dependency management
- Specify exact versions in `pom.xml`
- Group dependencies logically (Spring Boot, Database, Security, Documentation, Testing)
- Use Spring Boot BOM for version management
- Include only necessary dependencies

## Error Handling Standards
- **Global Exception Handler**: Centralized error handling
- **Custom Exceptions**: Domain-specific exceptions
- **Consistent Error Response**: Standard error response format
- **HTTP Status Codes**: Use appropriate status codes
- **Validation Errors**: Handle Bean Validation errors
- **Logging**: Log exceptions with appropriate levels

## Validation Rules for DTOs
- Use Bean Validation annotations (`@NotNull`, `@Size`, `@Min`, `@Max`, `@Pattern`)
- Validate at Controller level with `@Valid`
- Custom validation for business rules
- Clear error messages for validation failures

## Logging Standards
- Use SLF4J with Logback
- Log levels: ERROR, WARN, INFO, DEBUG
- Include correlation IDs for request tracing
- Log method entry/exit for debugging
- Don't log sensitive information

## Testing Strategy
- **Unit Tests**: Test individual components in isolation
- **Integration Tests**: Test component interactions
- **Testcontainers**: For database integration tests
- **Test Coverage**: Aim for 80%+ coverage
- **Test Data**: Use test fixtures and builders
- **Mocking**: Mock external dependencies

## Documentation Approach
- **OpenAPI 3**: API documentation with Swagger UI
- **README.md**: Project setup and usage instructions
- **Code Comments**: Javadoc for public APIs
- **API Examples**: Include request/response examples

## Code Quality Standards
- **Clean Code**: Readable, maintainable code
- **SOLID Principles**: Follow SOLID design principles
- **DRY**: Don't Repeat Yourself
- **KISS**: Keep It Simple, Stupid
- **Consistent Formatting**: Use consistent indentation and spacing
- **Meaningful Names**: Use descriptive variable and method names

## Security Standards
- **Input Validation**: Validate all inputs
- **Authentication**: Basic Auth for API access
- **Authorization**: Role-based access control
- **HTTPS**: Use HTTPS in production
- **No Sensitive Data in Logs**: Never log passwords or tokens

## Performance Standards
- **Database Queries**: Optimize queries, use indexes
- **Caching**: Use caching where appropriate
- **Connection Pooling**: Configure connection pools
- **Async Processing**: Use async for long-running operations
- **Monitoring**: Add health checks and metrics

## Deployment Standards
- **Docker**: Containerize the application
- **Environment Configuration**: Use environment-specific configs
- **Health Checks**: Implement health check endpoints
- **Graceful Shutdown**: Handle shutdown gracefully
- **Monitoring**: Add application metrics

## Phase 2 Implementation Status ✅

### Completed Features:
- ✅ **Spring Boot Project Structure**: Complete Maven project with proper directory structure
- ✅ **Book Entity**: JPA entity with validation annotations and audit fields
- ✅ **DTOs**: Request and Response DTOs with validation
- ✅ **Repository Layer**: JPA repository with custom search methods
- ✅ **Service Layer**: Business logic implementation with transaction management
- ✅ **Controller Layer**: REST endpoints with OpenAPI documentation
- ✅ **Authentication**: Spring Security with Basic Auth
- ✅ **Exception Handling**: Global exception handler with custom exceptions
- ✅ **Validation**: Bean Validation for input validation
- ✅ **Database**: H2 in-memory database with sample data
- ✅ **API Documentation**: OpenAPI 3 with Swagger UI
- ✅ **Configuration**: Application properties and security configuration
- ✅ **Logging**: Structured logging with SLF4J
- ✅ **Testing Support**: Test dependencies and structure
- ✅ **Git Integration**: Feature branch and pull request workflow

### Technical Implementation:
- **Java 17**: Latest LTS version
- **Spring Boot 3.2.4**: Latest stable version
- **Layered Architecture**: Controller → Service → Repository → Entity
- **REST API**: Complete CRUD operations with search capabilities
- **Security**: Basic authentication with in-memory users
- **Database**: H2 with automatic schema creation and data seeding
- **Documentation**: Comprehensive OpenAPI documentation
- **Error Handling**: Centralized exception handling with consistent responses
- **Validation**: Input validation with clear error messages
- **Logging**: Application and SQL logging for debugging

### API Endpoints Implemented:
- `POST /api/v1/books` - Create book
- `GET /api/v1/books` - Get all books
- `GET /api/v1/books/{id}` - Get book by ID
- `GET /api/v1/books/isbn/{isbn}` - Get book by ISBN
- `PUT /api/v1/books/{id}` - Update book
- `DELETE /api/v1/books/{id}` - Delete book
- `GET /api/v1/books/search?q={term}` - Search books
- `GET /api/v1/books/search/title?title={title}` - Search by title
- `GET /api/v1/books/search/author?author={author}` - Search by author
- `GET /api/v1/books/search/price-range?minPrice={min}&maxPrice={max}` - Search by price range
- `GET /api/v1/books/search/max-price?maxPrice={max}` - Search by max price
- `GET /api/v1/books/search/min-price?minPrice={min}` - Search by min price

### Authentication:
- **Admin User**: admin/admin123
- **Regular User**: user/user123

### Access Points:
- **API Base URL**: http://localhost:8080/api/v1/books
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
