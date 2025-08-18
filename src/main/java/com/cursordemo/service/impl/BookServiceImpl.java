package com.cursordemo.service.impl;

import com.cursordemo.dto.BookRequest;
import com.cursordemo.dto.BookResponse;
import com.cursordemo.entity.Book;
import com.cursordemo.exception.BookNotFoundException;
import com.cursordemo.exception.DuplicateIsbnException;
import com.cursordemo.repository.BookRepository;
import com.cursordemo.service.BookService;
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
 * This class provides the business logic for book operations including
 * CRUD operations, search functionality, and data validation.
 * 
 * @author Cursor Demo Team
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
    public BookResponse createBook(BookRequest bookRequest) {
        logger.info("Creating new book with ISBN: {}", bookRequest.getIsbn());
        
        // Check if book with ISBN already exists
        if (bookRepository.existsByIsbn(bookRequest.getIsbn())) {
            logger.error("Book with ISBN {} already exists", bookRequest.getIsbn());
            throw new DuplicateIsbnException("Book with ISBN " + bookRequest.getIsbn() + " already exists");
        }

        // Create new book entity
        Book book = new Book(
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getIsbn(),
                bookRequest.getPrice()
        );

        // Save and return
        Book savedBook = bookRepository.save(book);
        logger.info("Book created successfully with ID: {}", savedBook.getId());
        
        return BookResponse.fromBook(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookById(Long id) {
        logger.info("Fetching book by ID: {}", id);
        
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Book not found with ID: {}", id);
                    return new BookNotFoundException("Book not found with ID: " + id);
                });
        
        logger.info("Book found with ID: {}", id);
        return BookResponse.fromBook(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookByIsbn(String isbn) {
        logger.info("Fetching book by ISBN: {}", isbn);
        
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> {
                    logger.error("Book not found with ISBN: {}", isbn);
                    return new BookNotFoundException("Book not found with ISBN: " + isbn);
                });
        
        logger.info("Book found with ISBN: {}", isbn);
        return BookResponse.fromBook(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        logger.info("Fetching all books");
        
        List<Book> books = bookRepository.findAll();
        logger.info("Found {} books", books.size());
        
        return books.stream()
                .map(BookResponse::fromBook)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        logger.info("Updating book with ID: {}", id);
        
        // Check if book exists
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Book not found with ID: {}", id);
                    return new BookNotFoundException("Book not found with ID: " + id);
                });

        // Check if new ISBN conflicts with existing book (excluding current book)
        if (!existingBook.getIsbn().equals(bookRequest.getIsbn()) && 
            bookRepository.existsByIsbn(bookRequest.getIsbn())) {
            logger.error("Book with ISBN {} already exists", bookRequest.getIsbn());
            throw new DuplicateIsbnException("Book with ISBN " + bookRequest.getIsbn() + " already exists");
        }

        // Update book fields
        existingBook.setTitle(bookRequest.getTitle());
        existingBook.setAuthor(bookRequest.getAuthor());
        existingBook.setIsbn(bookRequest.getIsbn());
        existingBook.setPrice(bookRequest.getPrice());

        // Save and return
        Book updatedBook = bookRepository.save(existingBook);
        logger.info("Book updated successfully with ID: {}", id);
        
        return BookResponse.fromBook(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        
        // Check if book exists
        if (!bookRepository.existsById(id)) {
            logger.error("Book not found with ID: {}", id);
            throw new BookNotFoundException("Book not found with ID: " + id);
        }

        bookRepository.deleteById(id);
        logger.info("Book deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> searchBooksByTitle(String title) {
        logger.info("Searching books by title: {}", title);
        
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        logger.info("Found {} books matching title: {}", books.size(), title);
        
        return books.stream()
                .map(BookResponse::fromBook)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> searchBooksByAuthor(String author) {
        logger.info("Searching books by author: {}", author);
        
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
        logger.info("Found {} books matching author: {}", books.size(), author);
        
        return books.stream()
                .map(BookResponse::fromBook)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> searchBooks(String searchTerm) {
        logger.info("Searching books with term: {}", searchTerm);
        
        List<Book> books = bookRepository.searchBooks(searchTerm);
        logger.info("Found {} books matching search term: {}", books.size(), searchTerm);
        
        return books.stream()
                .map(BookResponse::fromBook)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> findBooksByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        logger.info("Searching books by price range: {} - {}", minPrice, maxPrice);
        
        List<Book> books = bookRepository.findByPriceRange(minPrice, maxPrice);
        logger.info("Found {} books in price range: {} - {}", books.size(), minPrice, maxPrice);
        
        return books.stream()
                .map(BookResponse::fromBook)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> findBooksByMaxPrice(BigDecimal maxPrice) {
        logger.info("Searching books with max price: {}", maxPrice);
        
        List<Book> books = bookRepository.findByPriceLessThanEqual(maxPrice);
        logger.info("Found {} books with max price: {}", books.size(), maxPrice);
        
        return books.stream()
                .map(BookResponse::fromBook)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> findBooksByMinPrice(BigDecimal minPrice) {
        logger.info("Searching books with min price: {}", minPrice);
        
        List<Book> books = bookRepository.findByPriceGreaterThanEqual(minPrice);
        logger.info("Found {} books with min price: {}", books.size(), minPrice);
        
        return books.stream()
                .map(BookResponse::fromBook)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByIsbn(String isbn) {
        logger.debug("Checking if book exists with ISBN: {}", isbn);
        return bookRepository.existsByIsbn(isbn);
    }
}
