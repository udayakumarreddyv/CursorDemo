# Cursor Demo - Spring Boot REST API

A comprehensive, production-ready REST API built with Spring Boot for managing books with CRUD operations and advanced search capabilities.

## 🚀 Features

- **Complete CRUD Operations**: Create, Read, Update, Delete books
- **Advanced Search**: Search by title, author, ISBN, and price range
- **Authentication**: Basic Authentication with Spring Security
- **API Documentation**: OpenAPI 3 (Swagger) documentation
- **Database**: H2 in-memory database with sample data
- **Validation**: Comprehensive input validation
- **Error Handling**: Global exception handling with consistent error responses
- **Logging**: Structured logging with SLF4J
- **Testing**: Unit and integration test support

## 🛠 Technology Stack

- **Java**: 17 (LTS)
- **Spring Boot**: 3.2.4
- **Spring Security**: Basic Authentication
- **Spring Data JPA**: Database operations
- **H2 Database**: In-memory database
- **Maven**: Build tool and dependency management
- **OpenAPI 3**: API documentation (Swagger)
- **Bean Validation**: Input validation
- **SLF4J + Logback**: Logging

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

The API uses Basic Authentication. Two users are configured:

- **Admin User**:
  - Username: `admin`
  - Password: `admin123`

- **Regular User**:
  - Username: `user`
  - Password: `user123`

## 📚 API Endpoints

### Base URL
```
http://localhost:8080/api/v1/books
```

### Available Endpoints

| Method | Endpoint | Description | Authentication Required |
|--------|----------|-------------|------------------------|
| POST | `/api/v1/books` | Create a new book | Yes |
| GET | `/api/v1/books` | Get all books | Yes |
| GET | `/api/v1/books/{id}` | Get book by ID | Yes |
| GET | `/api/v1/books/isbn/{isbn}` | Get book by ISBN | Yes |
| PUT | `/api/v1/books/{id}` | Update book | Yes |
| DELETE | `/api/v1/books/{id}` | Delete book | Yes |
| GET | `/api/v1/books/search?q={term}` | Search books | Yes |
| GET | `/api/v1/books/search/title?title={title}` | Search by title | Yes |
| GET | `/api/v1/books/search/author?author={author}` | Search by author | Yes |
| GET | `/api/v1/books/search/price-range?minPrice={min}&maxPrice={max}` | Search by price range | Yes |
| GET | `/api/v1/books/search/max-price?maxPrice={max}` | Search by max price | Yes |
| GET | `/api/v1/books/search/min-price?minPrice={min}` | Search by min price | Yes |

### Example API Calls

#### Create a Book
```bash
curl -X POST http://localhost:8080/api/v1/books \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
  -d '{
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "isbn": "9780743273565",
    "price": 12.99
  }'
```

#### Get All Books
```bash
curl -X GET http://localhost:8080/api/v1/books \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

#### Search Books
```bash
curl -X GET "http://localhost:8080/api/v1/books/search?q=Gatsby" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

## 📖 API Documentation

Once the application is running, you can access:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🗄 Database

The application uses H2 in-memory database with sample data. You can access the H2 console at:

- **H2 Console**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

## 🏗 Project Structure

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
│   │   │   ├── BookRequest.java
│   │   │   └── BookResponse.java
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── BookNotFoundException.java
│   │   │   └── DuplicateIsbnException.java
│   │   └── config/
│   │       ├── SecurityConfig.java
│   │       └── OpenApiConfig.java
│   └── resources/
│       ├── application.yml
│       └── data.sql
└── test/
    └── java/com/cursordemo/
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Run with Coverage
```bash
mvn clean test jacoco:report
```

## 🔧 Configuration

The application configuration is in `src/main/resources/application.yml`. Key configurations:

- **Server Port**: 8080
- **Database**: H2 in-memory
- **Logging**: DEBUG level for application packages
- **Security**: Basic authentication enabled

## 📝 Sample Data

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

## 🚀 Deployment

### Local Development
```bash
mvn spring-boot:run
```

### Production Build
```bash
mvn clean package
java -jar target/cursor-demo-1.0.0.jar
```

### Docker (Optional)
```bash
docker build -t cursor-demo .
docker run -p 8080:8080 cursor-demo
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

- **Cursor Demo Team** - *Initial work*

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- OpenAPI community for the documentation tools
- All contributors and reviewers

## 📞 Support

For support, email team@cursordemo.com or create an issue in the repository.

---

**Happy Coding! 🎉**