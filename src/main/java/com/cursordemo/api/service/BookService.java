package com.cursordemo.api.service;

import com.cursordemo.api.dto.BookRequestDto;
import com.cursordemo.api.dto.BookResponseDto;
import com.cursordemo.api.entity.Book;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for Book business logic.
 * 
 * This interface defines all the business operations that can be performed
 * on books, including CRUD operations and search functionality.
 * 
 * @author CursorDemo Team
 * @version 1.0.0
 */
public interface BookService {

    /**
     * Create a new book.
     * 
     * @param bookRequestDto the book data
     * @return the created book response
     */
    BookResponseDto createBook(BookRequestDto bookRequestDto);

    /**
     * Get a book by its ID.
     * 
     * @param id the book ID
     * @return the book response
     */
    BookResponseDto getBookById(Long id);

    /**
     * Get a book by its ISBN.
     * 
     * @param isbn the book ISBN
     * @return the book response
     */
    BookResponseDto getBookByIsbn(String isbn);

    /**
     * Get all books.
     * 
     * @return list of all books
     */
    List<BookResponseDto> getAllBooks();

    /**
     * Update a book by its ID.
     * 
     * @param id the book ID
     * @param bookRequestDto the updated book data
     * @return the updated book response
     */
    BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto);

    /**
     * Delete a book by its ID.
     * 
     * @param id the book ID
     */
    void deleteBook(Long id);

    /**
     * Search books by title.
     * 
     * @param title the title keyword
     * @return list of matching books
     */
    List<BookResponseDto> searchBooksByTitle(String title);

    /**
     * Search books by author.
     * 
     * @param author the author keyword
     * @return list of matching books
     */
    List<BookResponseDto> searchBooksByAuthor(String author);

    /**
     * Search books by title or author.
     * 
     * @param keyword the search keyword
     * @return list of matching books
     */
    List<BookResponseDto> searchBooks(String keyword);

    /**
     * Find books with price less than or equal to maxPrice.
     * 
     * @param maxPrice the maximum price
     * @return list of matching books
     */
    List<BookResponseDto> findBooksByMaxPrice(BigDecimal maxPrice);

    /**
     * Find books with price greater than or equal to minPrice.
     * 
     * @param minPrice the minimum price
     * @return list of matching books
     */
    List<BookResponseDto> findBooksByMinPrice(BigDecimal minPrice);

    /**
     * Find books with price between minPrice and maxPrice.
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of matching books
     */
    List<BookResponseDto> findBooksByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
}
