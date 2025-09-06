package com.cursordemo.service.impl;

import com.cursordemo.dto.BookRequestDTO;
import com.cursordemo.dto.BookResponseDTO;
import com.cursordemo.entity.Book;
import com.cursordemo.exception.BookNotFoundException;
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
 * Provides business logic for all book-related operations including
 * CRUD operations, search functionality, and data conversion.
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
    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) {
        logger.info("Creating new book with ISBN: {}", bookRequestDTO.getIsbn());
        
        // Check if book with same ISBN already exists
        if (bookRepository.existsByIsbn(bookRequestDTO.getIsbn())) {
            logger.warn("Book with ISBN {} already exists", bookRequestDTO.getIsbn());
            throw new IllegalArgumentException("Book with ISBN " + bookRequestDTO.getIsbn() + " already exists");
        }

        Book book = convertToEntity(bookRequestDTO);
        Book savedBook = bookRepository.save(book);
        
        logger.info("Book created successfully with ID: {}", savedBook.getId());
        return convertToResponseDTO(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDTO getBookById(Long id) {
        logger.info("Fetching book by ID: {}", id);
        
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Book not found with ID: {}", id);
                    return new BookNotFoundException("Book not found with ID: " + id);
                });
        
        logger.info("Book found with ID: {}", id);
        return convertToResponseDTO(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDTO getBookByIsbn(String isbn) {
        logger.info("Fetching book by ISBN: {}", isbn);
        
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> {
                    logger.warn("Book not found with ISBN: {}", isbn);
                    return new BookNotFoundException("Book not found with ISBN: " + isbn);
                });
        
        logger.info("Book found with ISBN: {}", isbn);
        return convertToResponseDTO(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> getAllBooks() {
        logger.info("Fetching all books");
        
        List<Book> books = bookRepository.findAll();
        logger.info("Found {} books", books.size());
        
        return books.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO) {
        logger.info("Updating book with ID: {}", id);
        
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Book not found with ID: {}", id);
                    return new BookNotFoundException("Book not found with ID: " + id);
                });

        // Check if ISBN is being changed and if the new ISBN already exists
        if (!existingBook.getIsbn().equals(bookRequestDTO.getIsbn()) && 
            bookRepository.existsByIsbn(bookRequestDTO.getIsbn())) {
            logger.warn("Book with ISBN {} already exists", bookRequestDTO.getIsbn());
            throw new IllegalArgumentException("Book with ISBN " + bookRequestDTO.getIsbn() + " already exists");
        }

        // Update book fields
        existingBook.setTitle(bookRequestDTO.getTitle());
        existingBook.setAuthor(bookRequestDTO.getAuthor());
        existingBook.setIsbn(bookRequestDTO.getIsbn());
        existingBook.setPrice(bookRequestDTO.getPrice());

        Book updatedBook = bookRepository.save(existingBook);
        
        logger.info("Book updated successfully with ID: {}", id);
        return convertToResponseDTO(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        
        if (!bookRepository.existsById(id)) {
            logger.warn("Book not found with ID: {}", id);
            throw new BookNotFoundException("Book not found with ID: " + id);
        }

        bookRepository.deleteById(id);
        logger.info("Book deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> searchBooksByTitle(String title) {
        logger.info("Searching books by title: {}", title);
        
        List<Book> books = bookRepository.findByTitleIgnoreCaseContaining(title);
        logger.info("Found {} books matching title: {}", books.size(), title);
        
        return books.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> searchBooksByAuthor(String author) {
        logger.info("Searching books by author: {}", author);
        
        List<Book> books = bookRepository.findByAuthorIgnoreCaseContaining(author);
        logger.info("Found {} books by author: {}", books.size(), author);
        
        return books.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> searchBooksByTitleOrAuthor(String title, String author) {
        logger.info("Searching books by title: {} or author: {}", title, author);
        
        List<Book> books = bookRepository.findByTitleOrAuthorContaining(title, author);
        logger.info("Found {} books matching title or author criteria", books.size());
        
        return books.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> searchBooksByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        logger.info("Searching books by price range: {} - {}", minPrice, maxPrice);
        
        List<Book> books = bookRepository.findByPriceBetween(minPrice, maxPrice);
        logger.info("Found {} books within price range", books.size());
        
        return books.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> searchBooksByMaxPrice(BigDecimal maxPrice) {
        logger.info("Searching books by max price: {}", maxPrice);
        
        List<Book> books = bookRepository.findByPriceLessThanEqualOrderByPrice(maxPrice);
        logger.info("Found {} books with price <= {}", books.size(), maxPrice);
        
        return books.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> searchBooksByMinPrice(BigDecimal minPrice) {
        logger.info("Searching books by min price: {}", minPrice);
        
        List<Book> books = bookRepository.findByPriceGreaterThanEqualOrderByPrice(minPrice);
        logger.info("Found {} books with price >= {}", books.size(), minPrice);
        
        return books.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean bookExistsByIsbn(String isbn) {
        logger.debug("Checking if book exists by ISBN: {}", isbn);
        return bookRepository.existsByIsbn(isbn);
    }

    @Override
    public BookResponseDTO convertToResponseDTO(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPrice(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }

    @Override
    public Book convertToEntity(BookRequestDTO bookRequestDTO) {
        return new Book(
                bookRequestDTO.getTitle(),
                bookRequestDTO.getAuthor(),
                bookRequestDTO.getIsbn(),
                bookRequestDTO.getPrice()
        );
    }
}
