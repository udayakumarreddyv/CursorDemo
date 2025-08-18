package com.cursordemo.controller;

import com.cursordemo.dto.BookRequest;
import com.cursordemo.dto.BookResponse;
import com.cursordemo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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
 * REST controller for Book operations.
 * 
 * This controller provides REST endpoints for managing books including
 * CRUD operations and search functionality. All endpoints are documented
 * with OpenAPI annotations.
 * 
 * @author Cursor Demo Team
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
     */
    @PostMapping
    @Operation(summary = "Create a new book", description = "Creates a new book with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully",
                    content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Book with ISBN already exists")
    })
    public ResponseEntity<BookResponse> createBook(
            @Parameter(description = "Book information", required = true)
            @Valid @RequestBody BookRequest bookRequest) {
        
        logger.info("Creating new book: {}", bookRequest.getTitle());
        BookResponse createdBook = bookService.createBook(bookRequest);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    /**
     * Get a book by its ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieves a book by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found",
                    content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponse> getBookById(
            @Parameter(description = "Book ID", required = true)
            @PathVariable Long id) {
        
        logger.info("Fetching book with ID: {}", id);
        BookResponse book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Get a book by its ISBN.
     */
    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Get book by ISBN", description = "Retrieves a book by its ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found",
                    content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponse> getBookByIsbn(
            @Parameter(description = "Book ISBN", required = true)
            @PathVariable String isbn) {
        
        logger.info("Fetching book with ISBN: {}", isbn);
        BookResponse book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    /**
     * Get all books.
     */
    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieves all books in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully",
                    content = @Content(schema = @Schema(implementation = BookResponse.class)))
    })
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        logger.info("Fetching all books");
        List<BookResponse> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Update a book by its ID.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Updates an existing book with new information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully",
                    content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "409", description = "Book with ISBN already exists")
    })
    public ResponseEntity<BookResponse> updateBook(
            @Parameter(description = "Book ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated book information", required = true)
            @Valid @RequestBody BookRequest bookRequest) {
        
        logger.info("Updating book with ID: {}", id);
        BookResponse updatedBook = bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Delete a book by its ID.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book", description = "Deletes a book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "Book ID", required = true)
            @PathVariable Long id) {
        
        logger.info("Deleting book with ID: {}", id);
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search books by title.
     */
    @GetMapping("/search/title")
    @Operation(summary = "Search books by title", description = "Searches for books by title (partial match)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = BookResponse.class)))
    })
    public ResponseEntity<List<BookResponse>> searchBooksByTitle(
            @Parameter(description = "Title to search for", required = true)
            @RequestParam String title) {
        
        logger.info("Searching books by title: {}", title);
        List<BookResponse> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    /**
     * Search books by author.
     */
    @GetMapping("/search/author")
    @Operation(summary = "Search books by author", description = "Searches for books by author (partial match)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = BookResponse.class)))
    })
    public ResponseEntity<List<BookResponse>> searchBooksByAuthor(
            @Parameter(description = "Author to search for", required = true)
            @RequestParam String author) {
        
        logger.info("Searching books by author: {}", author);
        List<BookResponse> books = bookService.searchBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    /**
     * Search books by general search term.
     */
    @GetMapping("/search")
    @Operation(summary = "Search books", description = "Searches for books by title, author, or ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = BookResponse.class)))
    })
    public ResponseEntity<List<BookResponse>> searchBooks(
            @Parameter(description = "Search term", required = true)
            @RequestParam String q) {
        
        logger.info("Searching books with term: {}", q);
        List<BookResponse> books = bookService.searchBooks(q);
        return ResponseEntity.ok(books);
    }

    /**
     * Find books by price range.
     */
    @GetMapping("/search/price-range")
    @Operation(summary = "Search books by price range", description = "Finds books within a specified price range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = BookResponse.class)))
    })
    public ResponseEntity<List<BookResponse>> findBooksByPriceRange(
            @Parameter(description = "Minimum price", required = true)
            @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximum price", required = true)
            @RequestParam BigDecimal maxPrice) {
        
        logger.info("Searching books by price range: {} - {}", minPrice, maxPrice);
        List<BookResponse> books = bookService.findBooksByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(books);
    }

    /**
     * Find books by maximum price.
     */
    @GetMapping("/search/max-price")
    @Operation(summary = "Search books by maximum price", description = "Finds books with price less than or equal to specified price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = BookResponse.class)))
    })
    public ResponseEntity<List<BookResponse>> findBooksByMaxPrice(
            @Parameter(description = "Maximum price", required = true)
            @RequestParam BigDecimal maxPrice) {
        
        logger.info("Searching books with max price: {}", maxPrice);
        List<BookResponse> books = bookService.findBooksByMaxPrice(maxPrice);
        return ResponseEntity.ok(books);
    }

    /**
     * Find books by minimum price.
     */
    @GetMapping("/search/min-price")
    @Operation(summary = "Search books by minimum price", description = "Finds books with price greater than or equal to specified price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = BookResponse.class)))
    })
    public ResponseEntity<List<BookResponse>> findBooksByMinPrice(
            @Parameter(description = "Minimum price", required = true)
            @RequestParam BigDecimal minPrice) {
        
        logger.info("Searching books with min price: {}", minPrice);
        List<BookResponse> books = bookService.findBooksByMinPrice(minPrice);
        return ResponseEntity.ok(books);
    }
}
