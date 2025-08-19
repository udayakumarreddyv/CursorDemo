package com.cursordemo.api.controller;

import com.cursordemo.api.dto.BookRequestDto;
import com.cursordemo.api.dto.BookResponseDto;
import com.cursordemo.api.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST controller for Book management operations.
 * 
 * This controller provides REST endpoints for CRUD operations on books,
 * including search functionality and price-based filtering.
 * 
 * @author CursorDemo Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Book Management", description = "APIs for managing books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Create a new book.
     * 
     * @param bookRequestDto the book data
     * @return the created book
     */
    @PostMapping
    @Operation(
            summary = "Create a new book",
            description = "Creates a new book with the provided information"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Book created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDto.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"title\": \"Sample Book\", \"author\": \"John Doe\", \"isbn\": \"1234567890123\", \"price\": 29.99}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Book with ISBN already exists"
            )
    })
    public ResponseEntity<BookResponseDto> createBook(
            @Parameter(description = "Book data", required = true)
            @Valid @RequestBody BookRequestDto bookRequestDto) {
        
        logger.info("Received request to create book with ISBN: {}", bookRequestDto.getIsbn());
        BookResponseDto createdBook = bookService.createBook(bookRequestDto);
        logger.info("Book created successfully with ID: {}", createdBook.getId());
        
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    /**
     * Get a book by its ID.
     * 
     * @param id the book ID
     * @return the book
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get a book by ID",
            description = "Retrieves a book by its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found"
            )
    })
    public ResponseEntity<BookResponseDto> getBookById(
            @Parameter(description = "Book ID", required = true)
            @PathVariable Long id) {
        
        logger.info("Received request to get book with ID: {}", id);
        BookResponseDto book = bookService.getBookById(id);
        logger.info("Book retrieved successfully with ID: {}", id);
        
        return ResponseEntity.ok(book);
    }

    /**
     * Get a book by its ISBN.
     * 
     * @param isbn the book ISBN
     * @return the book
     */
    @GetMapping("/isbn/{isbn}")
    @Operation(
            summary = "Get a book by ISBN",
            description = "Retrieves a book by its ISBN"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found"
            )
    })
    public ResponseEntity<BookResponseDto> getBookByIsbn(
            @Parameter(description = "Book ISBN", required = true)
            @PathVariable String isbn) {
        
        logger.info("Received request to get book with ISBN: {}", isbn);
        BookResponseDto book = bookService.getBookByIsbn(isbn);
        logger.info("Book retrieved successfully with ISBN: {}", isbn);
        
        return ResponseEntity.ok(book);
    }

    /**
     * Get all books.
     * 
     * @return list of all books
     */
    @GetMapping
    @Operation(
            summary = "Get all books",
            description = "Retrieves all books in the system"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Books retrieved successfully"
    )
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        logger.info("Received request to get all books");
        List<BookResponseDto> books = bookService.getAllBooks();
        logger.info("Retrieved {} books", books.size());
        
        return ResponseEntity.ok(books);
    }

    /**
     * Update a book by its ID.
     * 
     * @param id the book ID
     * @param bookRequestDto the updated book data
     * @return the updated book
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a book",
            description = "Updates an existing book with new information"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Book with ISBN already exists"
            )
    })
    public ResponseEntity<BookResponseDto> updateBook(
            @Parameter(description = "Book ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated book data", required = true)
            @Valid @RequestBody BookRequestDto bookRequestDto) {
        
        logger.info("Received request to update book with ID: {}", id);
        BookResponseDto updatedBook = bookService.updateBook(id, bookRequestDto);
        logger.info("Book updated successfully with ID: {}", id);
        
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Delete a book by its ID.
     * 
     * @param id the book ID
     * @return no content response
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a book",
            description = "Deletes a book by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Book deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found"
            )
    })
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "Book ID", required = true)
            @PathVariable Long id) {
        
        logger.info("Received request to delete book with ID: {}", id);
        bookService.deleteBook(id);
        logger.info("Book deleted successfully with ID: {}", id);
        
        return ResponseEntity.noContent().build();
    }

    /**
     * Search books by title.
     * 
     * @param title the title keyword
     * @return list of matching books
     */
    @GetMapping("/search/title")
    @Operation(
            summary = "Search books by title",
            description = "Searches for books by title containing the given keyword"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Search completed successfully"
    )
    public ResponseEntity<List<BookResponseDto>> searchBooksByTitle(
            @Parameter(description = "Title keyword", required = true)
            @RequestParam String title) {
        
        logger.info("Received request to search books by title: {}", title);
        List<BookResponseDto> books = bookService.searchBooksByTitle(title);
        logger.info("Found {} books matching title: {}", books.size(), title);
        
        return ResponseEntity.ok(books);
    }

    /**
     * Search books by author.
     * 
     * @param author the author keyword
     * @return list of matching books
     */
    @GetMapping("/search/author")
    @Operation(
            summary = "Search books by author",
            description = "Searches for books by author containing the given keyword"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Search completed successfully"
    )
    public ResponseEntity<List<BookResponseDto>> searchBooksByAuthor(
            @Parameter(description = "Author keyword", required = true)
            @RequestParam String author) {
        
        logger.info("Received request to search books by author: {}", author);
        List<BookResponseDto> books = bookService.searchBooksByAuthor(author);
        logger.info("Found {} books matching author: {}", books.size(), author);
        
        return ResponseEntity.ok(books);
    }

    /**
     * Search books by title or author.
     * 
     * @param keyword the search keyword
     * @return list of matching books
     */
    @GetMapping("/search")
    @Operation(
            summary = "Search books",
            description = "Searches for books by title or author containing the given keyword"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Search completed successfully"
    )
    public ResponseEntity<List<BookResponseDto>> searchBooks(
            @Parameter(description = "Search keyword", required = true)
            @RequestParam String keyword) {
        
        logger.info("Received request to search books by keyword: {}", keyword);
        List<BookResponseDto> books = bookService.searchBooks(keyword);
        logger.info("Found {} books matching keyword: {}", books.size(), keyword);
        
        return ResponseEntity.ok(books);
    }

    /**
     * Find books with price less than or equal to maxPrice.
     * 
     * @param maxPrice the maximum price
     * @return list of matching books
     */
    @GetMapping("/price/max")
    @Operation(
            summary = "Find books by maximum price",
            description = "Finds books with price less than or equal to the specified maximum price"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Search completed successfully"
    )
    public ResponseEntity<List<BookResponseDto>> findBooksByMaxPrice(
            @Parameter(description = "Maximum price", required = true)
            @RequestParam BigDecimal maxPrice) {
        
        logger.info("Received request to find books with max price: {}", maxPrice);
        List<BookResponseDto> books = bookService.findBooksByMaxPrice(maxPrice);
        logger.info("Found {} books with price <= {}", books.size(), maxPrice);
        
        return ResponseEntity.ok(books);
    }

    /**
     * Find books with price greater than or equal to minPrice.
     * 
     * @param minPrice the minimum price
     * @return list of matching books
     */
    @GetMapping("/price/min")
    @Operation(
            summary = "Find books by minimum price",
            description = "Finds books with price greater than or equal to the specified minimum price"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Search completed successfully"
    )
    public ResponseEntity<List<BookResponseDto>> findBooksByMinPrice(
            @Parameter(description = "Minimum price", required = true)
            @RequestParam BigDecimal minPrice) {
        
        logger.info("Received request to find books with min price: {}", minPrice);
        List<BookResponseDto> books = bookService.findBooksByMinPrice(minPrice);
        logger.info("Found {} books with price >= {}", books.size(), minPrice);
        
        return ResponseEntity.ok(books);
    }

    /**
     * Find books with price between minPrice and maxPrice.
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of matching books
     */
    @GetMapping("/price/range")
    @Operation(
            summary = "Find books by price range",
            description = "Finds books with price between the specified minimum and maximum prices"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Search completed successfully"
    )
    public ResponseEntity<List<BookResponseDto>> findBooksByPriceRange(
            @Parameter(description = "Minimum price", required = true)
            @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximum price", required = true)
            @RequestParam BigDecimal maxPrice) {
        
        logger.info("Received request to find books with price range: {} - {}", minPrice, maxPrice);
        List<BookResponseDto> books = bookService.findBooksByPriceRange(minPrice, maxPrice);
        logger.info("Found {} books with price between {} and {}", books.size(), minPrice, maxPrice);
        
        return ResponseEntity.ok(books);
    }
}
