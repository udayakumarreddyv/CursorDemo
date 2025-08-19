package com.cursordemo.api.service;

import com.cursordemo.api.dto.BookRequestDto;
import com.cursordemo.api.dto.BookResponseDto;
import com.cursordemo.api.entity.Book;
import com.cursordemo.api.exception.BookNotFoundException;
import com.cursordemo.api.exception.DuplicateIsbnException;
import com.cursordemo.api.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of BookService interface.
 * 
 * This class contains the business logic for all book-related operations,
 * including CRUD operations and search functionality.
 * 
 * @author CursorDemo Team
 * @version 1.0.0
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookResponseDto createBook(BookRequestDto bookRequestDto) {
        logger.info("Creating new book with ISBN: {}", bookRequestDto.getIsbn());
        
        // Check if book with same ISBN already exists
        if (bookRepository.existsByIsbn(bookRequestDto.getIsbn())) {
            throw new DuplicateIsbnException("Book with ISBN " + bookRequestDto.getIsbn() + " already exists");
        }

        // Create new book entity
        Book book = new Book(
                bookRequestDto.getTitle(),
                bookRequestDto.getAuthor(),
                bookRequestDto.getIsbn(),
                bookRequestDto.getPrice()
        );

        // Save the book
        Book savedBook = bookRepository.save(book);
        logger.info("Book created successfully with ID: {}", savedBook.getId());

        return convertToResponseDto(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDto getBookById(Long id) {
        logger.info("Fetching book by ID: {}", id);
        
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        return convertToResponseDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDto getBookByIsbn(String isbn) {
        logger.info("Fetching book by ISBN: {}", isbn);
        
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));

        return convertToResponseDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getAllBooks() {
        logger.info("Fetching all books");
        
        List<Book> books = bookRepository.findAll();
        logger.info("Found {} books", books.size());

        return books.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto) {
        logger.info("Updating book with ID: {}", id);
        
        // Check if book exists
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        // Check if ISBN is being changed and if new ISBN already exists
        if (!existingBook.getIsbn().equals(bookRequestDto.getIsbn()) &&
                bookRepository.existsByIsbn(bookRequestDto.getIsbn())) {
            throw new DuplicateIsbnException("Book with ISBN " + bookRequestDto.getIsbn() + " already exists");
        }

        // Update book fields
        existingBook.setTitle(bookRequestDto.getTitle());
        existingBook.setAuthor(bookRequestDto.getAuthor());
        existingBook.setIsbn(bookRequestDto.getIsbn());
        existingBook.setPrice(bookRequestDto.getPrice());

        // Save the updated book
        Book updatedBook = bookRepository.save(existingBook);
        logger.info("Book updated successfully with ID: {}", updatedBook.getId());

        return convertToResponseDto(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        
        // Check if book exists
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }

        bookRepository.deleteById(id);
        logger.info("Book deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> searchBooksByTitle(String title) {
        logger.info("Searching books by title: {}", title);
        
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        logger.info("Found {} books matching title: {}", books.size(), title);

        return books.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> searchBooksByAuthor(String author) {
        logger.info("Searching books by author: {}", author);
        
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
        logger.info("Found {} books matching author: {}", books.size(), author);

        return books.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> searchBooks(String keyword) {
        logger.info("Searching books by keyword: {}", keyword);
        
        List<Book> books = bookRepository.findByTitleOrAuthorContainingIgnoreCase(keyword);
        logger.info("Found {} books matching keyword: {}", books.size(), keyword);

        return books.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> findBooksByMaxPrice(BigDecimal maxPrice) {
        logger.info("Finding books with max price: {}", maxPrice);
        
        List<Book> books = bookRepository.findByPriceLessThanEqual(maxPrice);
        logger.info("Found {} books with price <= {}", books.size(), maxPrice);

        return books.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> findBooksByMinPrice(BigDecimal minPrice) {
        logger.info("Finding books with min price: {}", minPrice);
        
        List<Book> books = bookRepository.findByPriceGreaterThanEqual(minPrice);
        logger.info("Found {} books with price >= {}", books.size(), minPrice);

        return books.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> findBooksByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        logger.info("Finding books with price range: {} - {}", minPrice, maxPrice);
        
        List<Book> books = bookRepository.findByPriceBetween(minPrice, maxPrice);
        logger.info("Found {} books with price between {} and {}", books.size(), minPrice, maxPrice);

        return books.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Convert Book entity to BookResponseDto.
     * 
     * @param book the book entity
     * @return the book response DTO
     */
    private BookResponseDto convertToResponseDto(Book book) {
        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPrice(),
                book.getCreatedAt(),
                book.getUpdatedAt(),
                book.getVersion()
        );
    }
}
