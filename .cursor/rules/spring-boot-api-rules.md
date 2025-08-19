# Spring Boot API Development Rules

## Architecture Style
- **Layered Architecture**: Controller → Service → Repository → DTOs → Entities
- **Separation of Concerns**: Each layer has a specific responsibility
- **Dependency Injection**: Use Spring's DI container for loose coupling
- **RESTful Design**: Follow REST principles for API endpoints

## Naming Conventions

### Classes
- **Controllers**: `{Entity}Controller` (e.g., `BookController`)
- **Services**: `{Entity}Service` (e.g., `BookService`)
- **Repositories**: `{Entity}Repository` (e.g., `BookRepository`)
- **Entities**: `{Entity}` (e.g., `Book`)
- **DTOs**: `{Entity}DTO`, `{Entity}RequestDTO`, `{Entity}ResponseDTO`
- **Exceptions**: `{Entity}Exception`, `{Entity}NotFoundException`
- **Configurations**: `{Purpose}Config` (e.g., `SecurityConfig`)

### Methods
- **Controllers**: HTTP verb + entity (e.g., `getBook`, `createBook`, `updateBook`, `deleteBook`)
- **Services**: Business logic methods (e.g., `findById`, `save`, `update`, `delete`)
- **Repositories**: Use Spring Data JPA naming conventions

### Variables
- **Camel Case**: `bookTitle`, `authorName`
- **Constants**: `UPPER_SNAKE_CASE`
- **Package names**: `lowercase.with.dots`

## Technology Stack
- **Java**: 17
- **Spring Boot**: Latest stable version (3.x)
- **Database**: H2 (in-memory for development)
- **Authentication**: Basic Auth with Spring Security
- **Build Tool**: Maven
- **Documentation**: Swagger/OpenAPI 3
- **Testing**: JUnit 5 + Mockito

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
│   │           │   ├── BookDTO.java
│   │           │   ├── BookRequestDTO.java
│   │           │   └── BookResponseDTO.java
│   │           ├── exception/
│   │           │   ├── GlobalExceptionHandler.java
│   │           │   ├── BookNotFoundException.java
│   │           │   └── ValidationException.java
│   │           └── config/
│   │               ├── SecurityConfig.java
│   │               └── SwaggerConfig.java
│   └── resources/
│       ├── application.yml
│       └── data.sql
└── test/
    └── java/
        └── com/
            └── cursordemo/
                ├── controller/
                │   └── BookControllerTest.java
                ├── service/
                │   └── BookServiceTest.java
                └── repository/
                    └── BookRepositoryTest.java
```

## Dependency Management Rules (Maven)
- Use Spring Boot parent POM for dependency management
- Specify exact versions for critical dependencies
- Group dependencies by purpose (spring-boot-starter-*)
- Include test dependencies for comprehensive testing

## Error Handling Standards
- **Custom Exceptions**: Create specific exceptions for different error scenarios
- **Global Exception Handler**: Centralized error handling with proper HTTP status codes
- **Validation Errors**: Use `@Valid` annotations with proper error messages
- **Error Response Format**: Consistent error response structure

## Validation Rules for DTOs
- Use Bean Validation annotations (`@NotNull`, `@Size`, `@Min`, `@Max`, etc.)
- Custom validation for business rules
- Clear error messages for validation failures
- Separate request and response DTOs

## Logging Standards
- Use SLF4J with Logback
- Log levels: ERROR, WARN, INFO, DEBUG
- Include request/response logging for debugging
- Log exceptions with stack traces
- Use structured logging for better analysis

## Testing Strategy
- **Unit Tests**: Test individual components in isolation
- **Service Layer**: Mock dependencies and test business logic
- **Controller Layer**: Test HTTP endpoints with MockMvc
- **Repository Layer**: Test data access with @DataJpaTest
- **Test Coverage**: Aim for 80%+ code coverage
- **Test Naming**: `{MethodName}_{Scenario}_{ExpectedResult}`

## Documentation Approach
- **Swagger/OpenAPI 3**: Auto-generate API documentation
- **Controller Documentation**: Use `@Operation`, `@Parameter` annotations
- **DTO Documentation**: Use `@Schema` annotations
- **README.md**: Project setup, running instructions, API overview

## Code Quality Standards
- **Clean Code**: Readable, maintainable, and self-documenting code
- **SOLID Principles**: Follow single responsibility, open/closed, etc.
- **DRY Principle**: Don't repeat yourself
- **Consistent Formatting**: Use IDE auto-formatting
- **Meaningful Comments**: Comment complex business logic only

## Security Standards
- **Basic Authentication**: Implement with Spring Security
- **Input Validation**: Validate all inputs to prevent injection attacks
- **Error Handling**: Don't expose sensitive information in error messages
- **CORS Configuration**: Configure if needed for frontend integration

## Performance Considerations
- **Database Indexing**: Add indexes for frequently queried fields
- **Pagination**: Implement for large datasets
- **Caching**: Use Spring Cache where appropriate
- **Connection Pooling**: Configure HikariCP properly

## Git Workflow
- **Feature Branches**: Create feature branches for new functionality
- **Commit Messages**: Use conventional commit format
- **Pull Requests**: Create PRs for code review
- **Branch Naming**: `feature/{description}` or `fix/{description}`
