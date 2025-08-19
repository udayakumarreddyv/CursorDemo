# CursorDemo Book Management API

A comprehensive REST API for managing books built with Spring Boot, featuring CRUD operations, search functionality, and price-based filtering.

## 🚀 Features

- **CRUD Operations**: Create, Read, Update, and Delete books
- **Search Functionality**: Search books by title, author, or both
- **Price Filtering**: Find books by price range, minimum, or maximum price
- **Basic Authentication**: Secure API access with username/password
- **H2 In-Memory Database**: Fast development and testing
- **Swagger Documentation**: Interactive API documentation
- **Comprehensive Testing**: Unit tests with high coverage
- **Global Exception Handling**: Consistent error responses
- **Input Validation**: Bean validation for all inputs
- **Audit Fields**: Automatic creation and update timestamps

## 🛠 Technology Stack

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring Security**: Basic Authentication
- **Spring Data JPA**: Data access layer
- **H2 Database**: In-memory database
- **Maven**: Build tool
- **Swagger/OpenAPI**: API documentation
- **JUnit 5**: Testing framework
- **Mockito**: Mocking framework

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

The API uses Basic Authentication. Use the following credentials:

- **Username**: `admin` | **Password**: `admin123`
- **Username**: `user` | **Password**: `user123`

## 📚 API Endpoints

### Base URL
```
http://localhost:8080/api/v1/books
```

### Available Endpoints

#### CRUD Operations

| Method | Endpoint | Description | Authentication |
|--------|----------|-------------|----------------|
| POST | `/api/v1/books` | Create a new book | Required |
| GET | `/api/v1/books` | Get all books | Required |
| GET | `/api/v1/books/{id}` | Get book by ID | Required |
| GET | `/api/v1/books/isbn/{isbn}` | Get book by ISBN | Required |
| PUT | `/api/v1/books/{id}` | Update book | Required |
| DELETE | `/api/v1/books/{id}` | Delete book | Required |

#### Search Operations

| Method | Endpoint | Description | Authentication |
|--------|----------|-------------|----------------|
| GET | `/api/v1/books/search?keyword={keyword}` | Search by title or author | Required |
| GET | `/api/v1/books/search/title?title={title}` | Search by title | Required |
| GET | `/api/v1/books/search/author?author={author}` | Search by author | Required |

#### Price Filtering

| Method | Endpoint | Description | Authentication |
|--------|----------|-------------|----------------|
| GET | `/api/v1/books/price/max?maxPrice={price}` | Books with price ≤ maxPrice | Required |
| GET | `/api/v1/books/price/min?minPrice={price}` | Books with price ≥ minPrice | Required |
| GET | `/api/v1/books/price/range?minPrice={min}&maxPrice={max}` | Books within price range | Required |

## 📖 API Documentation

### Swagger UI
Access the interactive API documentation at:
```
http://localhost:8080/swagger-ui.html
```

### OpenAPI Specification
Download the OpenAPI specification at:
```
http://localhost:8080/v3/api-docs
```

## 🗄️ Database

### H2 Console
Access the H2 database console at:
```
http://localhost:8080/h2-console
```

**Connection Details:**
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

### Sample Data
The application comes with 10 sample books pre-loaded:
- The Great Gatsby
- To Kill a Mockingbird
- 1984
- Pride and Prejudice
- The Hobbit
- The Catcher in the Rye
- Lord of the Flies
- Animal Farm
- The Alchemist
- Brave New World

## 📝 Request/Response Examples

### Create a Book

**Request:**
```bash
curl -X POST "http://localhost:8080/api/v1/books" \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
  -d '{
    "title": "Sample Book",
    "author": "John Doe",
    "isbn": "1234567890123",
    "price": 29.99
  }'
```

**Response:**
```json
{
  "id": 1,
  "title": "Sample Book",
  "author": "John Doe",
  "isbn": "1234567890123",
  "price": 29.99,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "version": 0
}
```

### Get All Books

**Request:**
```bash
curl -X GET "http://localhost:8080/api/v1/books" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

### Search Books

**Request:**
```bash
curl -X GET "http://localhost:8080/api/v1/books/search?keyword=Orwell" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Test Coverage
The project includes comprehensive unit tests covering:
- Service layer business logic
- Exception handling
- Data validation
- CRUD operations
- Search functionality

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/cursordemo/api/
│   │   ├── CursorDemoApplication.java
│   │   ├── controller/
│   │   │   └── BookController.java
│   │   ├── service/
│   │   │   ├── BookService.java
│   │   │   └── BookServiceImpl.java
│   │   ├── repository/
│   │   │   └── BookRepository.java
│   │   ├── entity/
│   │   │   └── Book.java
│   │   ├── dto/
│   │   │   ├── BookRequestDto.java
│   │   │   └── BookResponseDto.java
│   │   ├── exception/
│   │   │   ├── BookNotFoundException.java
│   │   │   ├── DuplicateIsbnException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── config/
│   │   │   └── OpenApiConfig.java
│   │   └── security/
│   │       └── SecurityConfig.java
│   └── resources/
│       ├── application.yml
│       └── data.sql
└── test/
    └── java/com/cursordemo/api/
        └── service/
            └── BookServiceTest.java
```

## 🔧 Configuration

### Application Properties
Key configuration options in `application.yml`:

```yaml
# Server Configuration
server:
  port: 8080

# Database Configuration
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password: password

# H2 Console
spring:
  h2:
    console:
      enabled: true
      path: /h2-console

# Swagger Configuration
springdoc:
  swagger-ui:
    path: /swagger-ui.html
```

## 🚀 Deployment

### Build JAR
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/api-1.0.0.jar
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **CursorDemo Team** - *Initial work*

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- H2 Database for the in-memory database
- Swagger team for the API documentation tools

## 📞 Support

For support and questions:
- Create an issue in the GitHub repository
- Contact: team@cursordemo.com

---

**Happy Coding! 🎉**