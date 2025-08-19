# Cursor Demo - Spring Boot Book Management API

A comprehensive REST API for managing books with CRUD operations and advanced search functionality, built with Spring Boot 3.x and Java 17.

## 🚀 Features

- **Complete CRUD Operations**: Create, Read, Update, Delete books
- **Advanced Search**: Search by title, author, price range, and more
- **Authentication**: Basic authentication with Spring Security
- **Validation**: Comprehensive input validation with Bean Validation
- **Documentation**: Auto-generated API documentation with Swagger/OpenAPI 3
- **Database**: H2 in-memory database with sample data
- **Testing**: Comprehensive unit tests with JUnit 5 and Mockito
- **Error Handling**: Global exception handling with proper HTTP status codes
- **Logging**: Structured logging with SLF4J and Logback

## 🛠️ Technology Stack

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring Security**: Basic authentication
- **Spring Data JPA**: Database operations
- **H2 Database**: In-memory database
- **Maven**: Build tool and dependency management
- **Swagger/OpenAPI 3**: API documentation
- **JUnit 5 + Mockito**: Testing framework
- **Bean Validation**: Input validation

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Git

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/udayakumarreddyv/CursorDemo.git
cd CursorDemo
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🔐 Authentication

The API uses Basic Authentication with the following default users:

| Username | Password | Role |
|----------|----------|------|
| admin    | admin123 | ADMIN |
| user     | user123  | USER |

## 📚 API Documentation

Once the application is running, you can access:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **H2 Console**: http://localhost:8080/h2-console

### H2 Database Console Access
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

## 📖 API Endpoints

### Book Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/books` | Create a new book |
| GET | `/api/v1/books` | Get all books |
| GET | `/api/v1/books/{id}` | Get book by ID |
| GET | `/api/v1/books/isbn/{isbn}` | Get book by ISBN |
| PUT | `/api/v1/books/{id}` | Update book by ID |
| DELETE | `/api/v1/books/{id}` | Delete book by ID |

### Search Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/books/search/title?title={title}` | Search books by title |
| GET | `/api/v1/books/search/author?author={author}` | Search books by author |
| GET | `/api/v1/books/search?title={title}&author={author}` | Search books by title or author |
| GET | `/api/v1/books/search/price-range?minPrice={min}&maxPrice={max}` | Search books by price range |
| GET | `/api/v1/books/search/price-max?maxPrice={max}` | Search books by maximum price |
| GET | `/api/v1/books/search/price-min?minPrice={min}` | Search books by minimum price |
| GET | `/api/v1/books/exists/{isbn}` | Check if book exists by ISBN |

## 📝 API Examples

### Create a Book

```bash
curl -X POST http://localhost:8080/api/v1/books \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
  -d '{
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "isbn": "978-0743273565",
    "price": 29.99
  }'
```

### Get All Books

```bash
curl -X GET http://localhost:8080/api/v1/books \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

### Search Books by Title

```bash
curl -X GET "http://localhost:8080/api/v1/books/search/title?title=Gatsby" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

### Update a Book

```bash
curl -X PUT http://localhost:8080/api/v1/books/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
  -d '{
    "title": "The Great Gatsby (Updated)",
    "author": "F. Scott Fitzgerald",
    "isbn": "978-0743273565",
    "price": 34.99
  }'
```

### Delete a Book

```bash
curl -X DELETE http://localhost:8080/api/v1/books/1 \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/cursordemo/
│   │   ├── CursorDemoApplication.java
│   │   ├── controller/
│   │   │   └── BookController.java
│   │   ├── service/
│   │   │   ├── BookService.java
│   │   │   └── impl/
│   │   │       └── BookServiceImpl.java
│   │   ├── repository/
│   │   │   └── BookRepository.java
│   │   ├── entity/
│   │   │   └── Book.java
│   │   ├── dto/
│   │   │   ├── BookRequestDTO.java
│   │   │   └── BookResponseDTO.java
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── BookNotFoundException.java
│   │   │   └── ValidationException.java
│   │   └── config/
│   │       ├── SecurityConfig.java
│   │       └── SwaggerConfig.java
│   └── resources/
│       ├── application.yml
│       └── data.sql
└── test/
    └── java/com/cursordemo/
        ├── controller/
        │   └── BookControllerTest.java
        ├── service/
        │   └── BookServiceTest.java
        └── repository/
            └── BookRepositoryTest.java
```

## 🧪 Testing

### Run All Tests

```bash
mvn test
```

### Run Tests with Coverage

```bash
mvn test jacoco:report
```

### Run Specific Test Class

```bash
mvn test -Dtest=BookControllerTest
```

## 📊 Sample Data

The application comes with 10 sample books pre-loaded:

1. The Great Gatsby - F. Scott Fitzgerald
2. To Kill a Mockingbird - Harper Lee
3. 1984 - George Orwell
4. Pride and Prejudice - Jane Austen
5. The Hobbit - J.R.R. Tolkien
6. The Catcher in the Rye - J.D. Salinger
7. Lord of the Flies - William Golding
8. Animal Farm - George Orwell
9. The Alchemist - Paulo Coelho
10. Brave New World - Aldous Huxley

## 🔧 Configuration

### Application Properties

Key configuration options in `application.yml`:

- **Server Port**: 8080
- **Database**: H2 in-memory
- **Logging**: DEBUG level for application packages
- **Swagger**: Enabled with custom configuration

### Customization

You can customize the application by modifying:

- `src/main/resources/application.yml` - Application configuration
- `src/main/resources/data.sql` - Sample data
- `src/main/java/com/cursordemo/config/SecurityConfig.java` - Security settings
- `src/main/java/com/cursordemo/config/SwaggerConfig.java` - API documentation

## 🚨 Error Handling

The API provides comprehensive error handling with proper HTTP status codes:

- **400 Bad Request**: Validation errors, invalid input
- **401 Unauthorized**: Missing or invalid authentication
- **404 Not Found**: Resource not found
- **409 Conflict**: Duplicate ISBN
- **500 Internal Server Error**: Unexpected server errors

## 📈 Performance Considerations

- **Database Indexing**: ISBN field is indexed for fast lookups
- **Connection Pooling**: HikariCP configured for optimal performance
- **Caching**: Ready for Spring Cache integration
- **Pagination**: Framework ready for large dataset pagination

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support and questions:

- Create an issue in the GitHub repository
- Contact: support@cursordemo.com
- Documentation: [API Documentation](http://localhost:8080/swagger-ui.html)

## 🔄 Version History

- **v1.0.0**: Initial release with complete CRUD operations and search functionality

---

**Happy Coding! 🎉**