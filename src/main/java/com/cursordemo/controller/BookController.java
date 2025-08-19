package com.cursordemo.controller;

import com.cursordemo.dto.BookRequestDTO;
import com.cursordemo.dto.BookResponseDTO;
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
 * REST Controller for Book operations.
 * 
 * Provides REST endpoints for all book-related operations including
 * CRUD operations and search functionality with comprehensive documentation.
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
    @Operation(summary = "Create a new book", description = "Creates a new book with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully",
                    content = @Content(schema = @Schema(implementation = BookResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Book with ISBN already exists")
    })
    public ResponseEntity<BookResponseDTO> createBook(
            @Parameter(description = "Book data to create", required = true)
            @Valid @RequestBody BookRequestDTO bookRequestDTO) {
        
        logger.info("Creating new book with title: {}", bookRequestDTO.getTitle());
        BookResponseDTO createdBook = bookService.createBook(bookRequestDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    /**
     * Get a book by ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieves a book by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found",
                    content = @Content(schema = @Schema(implementation = BookResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponseDTO> getBookById(
            @Parameter(description = "Book ID", required = true)
            @PathVariable Long id) {
        
        logger.info("Fetching book with ID: {}", id);
        BookResponseDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Get a book by ISBN.
     */
    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Get book by ISBN", description = "Retrieves a book by its ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found",
                    content = @Content(schema = @Schema(implementation = BookResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponseDTO> getBookByIsbn(
            @Parameter(description = "Book ISBN", required = true)
            @PathVariable String isbn) {
        
        logger.info("Fetching book with ISBN: {}", isbn);
        BookResponseDTO book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    /**
     * Get all books.
     */
    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieves all books in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully",
                    content = @Content(schema = @Schema(implementation = BookResponseDTO.class)))
    })
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        logger.info("Fetching all books");
        List<BookResponseDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Update a book by ID.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update book by ID", description = "Updates an existing book with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully",
                    content = @Content(schema = @Schema(implementation = BookResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "409", description = "Book with ISBN already exists")
    })
    public ResponseEntity<BookResponseDTO> updateBook(
            @Parameter(description = "Book ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated book data", required = true)
            @Valid @RequestBody BookRequestDTO bookRequestDTO) {
        
        logger.info("Updating book with ID: {}", id);
        BookResponseDTO updatedBook = bookService.updateBook(id, bookRequestDTO);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Delete a book by ID.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book by ID", description = "Deletes a book by its unique identifier")
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
    @Operation(summary = "Search books by title", description = "Searches for books by title (case-insensitive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = BookResponseDTO.class)))
    })
    public ResponseEntity<List<BookResponseDTO>> searchBooksByTitle(
            @Parameter(description = "Title to search for", required = true)
            @RequestParam String title) {
        
        logger.info("Searching books by title: {}", title);
        List<BookResponseDTO> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    /**
     * Search books by author.
     */
    @GetMapping("/search/author")
    @Operation(summary = "Search books by author", description = "Searches for books by author (case-insensitive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = BookResponseDTO.class)))
    })
    public ResponseEntity<List<BookResponseDTO>> searchBooksByAuthor(
            @Parameter(description = "Author to search for", required = true)
            @RequestParam String author) {
        
        logger.info("Searching books by author: {}", author);
        List<BookResponseDTO> books = bookService.searchBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    /**
     * Search books by title or author.
     */
    @GetMapping("/search")
    @Operation(summary = "Search books by title or author", description = "Searches for books by title or author (case-insensitive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = BookResponseDTO.class)))
    })
    public ResponseEntity<List<BookResponseDTO>> searchBooksByTitleOrAuthor(
            @Parameter(description = "Title to search for")
            @RequestParam(required = false) String title,
            @Parameter(description = "Author to search for")
            @RequestParam(required = false) String author) {
        
        logger.info("Searching books by title: {} or author: {}", title, author);
        List<BookResponseDTO> books = bookService.searchBooksByTitleOrAuthor(title, author);
        return ResponseEntity.ok(books);
    }

    /**
     * Search books by price range.
     */
    @GetMapping("/search/price-range")
    @Operation(summary = "Search books by price range", description = "Searches for books within a specified price range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = BookResponseDTO.class)))
    })
    public ResponseEntity<List<BookResponseDTO>> searchBooksByPriceRange(
            @Parameter(description = "Minimum price", required = true)
            @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximum price", required = true)
            @RequestParam BigDecimal maxPrice) {
        
        logger.info("Searching books by price range: {} - {}", minPrice, maxPrice);
        List<BookResponseDTO> books = bookService.searchBooksByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(books);
    }

    /**
     * Search books by maximum price.
     */
    @GetMapping("/search/price-max")
    @Operation(summary = "Search books by maximum price", description = "Searches for books with price less than or equal to the specified price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = BookResponseDTO.class)))
    })
    public ResponseEntity<List<BookResponseDTO>> searchBooksByMaxPrice(
            @Parameter(description = "Maximum price", required = true)
            @RequestParam BigDecimal maxPrice) {
        
        logger.info("Searching books by max price: {}", maxPrice);
        List<BookResponseDTO> books = bookService.searchBooksByMaxPrice(maxPrice);
        return ResponseEntity.ok(books);
    }

    /**
     * Search books by minimum price.
     */
    @GetMapping("/search/price-min")
    @Operation(summary = "Search books by minimum price", description = "Searches for books with price greater than or equal to the specified price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = BookResponseDTO.class)))
    })
    public ResponseEntity<List<BookResponseDTO>> searchBooksByMinPrice(
            @Parameter(description = "Minimum price", required = true)
            @RequestParam BigDecimal minPrice) {
        
        logger.info("Searching books by min price: {}", minPrice);
        List<BookResponseDTO> books = bookService.searchBooksByMinPrice(minPrice);
        return ResponseEntity.ok(books);
    }

    /**
     * Check if a book exists by ISBN.
     */
    @GetMapping("/exists/{isbn}")
    @Operation(summary = "Check if book exists by ISBN", description = "Checks if a book exists in the system by its ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check completed successfully")
    })
    public ResponseEntity<Boolean> bookExistsByIsbn(
            @Parameter(description = "Book ISBN", required = true)
            @PathVariable String isbn) {
        
        logger.info("Checking if book exists by ISBN: {}", isbn);
        boolean exists = bookService.bookExistsByIsbn(isbn);
        return ResponseEntity.ok(exists);
    }
}
