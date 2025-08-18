package com.cursordemo.service;

import com.cursordemo.dto.BookRequest;
import com.cursordemo.dto.BookResponse;
import com.cursordemo.entity.Book;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for Book business operations.
 * 
 * This interface defines the contract for book-related business operations
 * including CRUD operations and search functionality.
 * 
 * @author Cursor Demo Team
 * @version 1.0.0
 */
public interface BookService {

    /**
     * Create a new book.
     * 
     * @param bookRequest the book data
     * @return the created book response
     */
    BookResponse createBook(BookRequest bookRequest);

    /**
     * Get a book by its ID.
     * 
     * @param id the book ID
     * @return the book response
     * @throws com.cursordemo.exception.BookNotFoundException if book not found
     */
    BookResponse getBookById(Long id);

    /**
     * Get a book by its ISBN.
     * 
     * @param isbn the book ISBN
     * @return the book response
     * @throws com.cursordemo.exception.BookNotFoundException if book not found
     */
    BookResponse getBookByIsbn(String isbn);

    /**
     * Get all books.
     * 
     * @return list of all books
     */
    List<BookResponse> getAllBooks();

    /**
     * Update a book by its ID.
     * 
     * @param id the book ID
     * @param bookRequest the updated book data
     * @return the updated book response
     * @throws com.cursordemo.exception.BookNotFoundException if book not found
     */
    BookResponse updateBook(Long id, BookRequest bookRequest);

    /**
     * Delete a book by its ID.
     * 
     * @param id the book ID
     * @throws com.cursordemo.exception.BookNotFoundException if book not found
     */
    void deleteBook(Long id);

    /**
     * Search books by title.
     * 
     * @param title the title to search for
     * @return list of matching books
     */
    List<BookResponse> searchBooksByTitle(String title);

    /**
     * Search books by author.
     * 
     * @param author the author to search for
     * @return list of matching books
     */
    List<BookResponse> searchBooksByAuthor(String author);

    /**
     * Search books by general search term (title, author, or ISBN).
     * 
     * @param searchTerm the term to search for
     * @return list of matching books
     */
    List<BookResponse> searchBooks(String searchTerm);

    /**
     * Find books within a price range.
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of books within the price range
     */
    List<BookResponse> findBooksByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Find books with price less than or equal to the specified price.
     * 
     * @param maxPrice the maximum price
     * @return list of books with price <= maxPrice
     */
    List<BookResponse> findBooksByMaxPrice(BigDecimal maxPrice);

    /**
     * Find books with price greater than or equal to the specified price.
     * 
     * @param minPrice the minimum price
     * @return list of books with price >= minPrice
     */
    List<BookResponse> findBooksByMinPrice(BigDecimal minPrice);

    /**
     * Check if a book exists by ISBN.
     * 
     * @param isbn the ISBN to check
     * @return true if a book with the ISBN exists, false otherwise
     */
    boolean existsByIsbn(String isbn);
}
