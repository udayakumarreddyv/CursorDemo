package com.cursordemo.service;

import com.cursordemo.dto.BookRequestDTO;
import com.cursordemo.dto.BookResponseDTO;
import com.cursordemo.entity.Book;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for Book business operations.
 * 
 * Defines the contract for all book-related business logic including
 * CRUD operations and search functionality.
 */
public interface BookService {

    /**
     * Create a new book.
     * 
     * @param bookRequestDTO the book data to create
     * @return the created book response
     */
    BookResponseDTO createBook(BookRequestDTO bookRequestDTO);

    /**
     * Get a book by its ID.
     * 
     * @param id the book ID
     * @return the book response
     * @throws com.cursordemo.exception.BookNotFoundException if book not found
     */
    BookResponseDTO getBookById(Long id);

    /**
     * Get a book by its ISBN.
     * 
     * @param isbn the book ISBN
     * @return the book response
     * @throws com.cursordemo.exception.BookNotFoundException if book not found
     */
    BookResponseDTO getBookByIsbn(String isbn);

    /**
     * Get all books.
     * 
     * @return list of all books
     */
    List<BookResponseDTO> getAllBooks();

    /**
     * Update a book by its ID.
     * 
     * @param id the book ID
     * @param bookRequestDTO the updated book data
     * @return the updated book response
     * @throws com.cursordemo.exception.BookNotFoundException if book not found
     */
    BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO);

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
     * @return list of books matching the title
     */
    List<BookResponseDTO> searchBooksByTitle(String title);

    /**
     * Search books by author.
     * 
     * @param author the author to search for
     * @return list of books by the author
     */
    List<BookResponseDTO> searchBooksByAuthor(String author);

    /**
     * Search books by title or author.
     * 
     * @param title the title to search for
     * @param author the author to search for
     * @return list of books matching either title or author
     */
    List<BookResponseDTO> searchBooksByTitleOrAuthor(String title, String author);

    /**
     * Search books by price range.
     * 
     * @param minPrice minimum price (inclusive)
     * @param maxPrice maximum price (inclusive)
     * @return list of books within the price range
     */
    List<BookResponseDTO> searchBooksByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Search books by maximum price.
     * 
     * @param maxPrice maximum price (inclusive)
     * @return list of books with price <= maxPrice
     */
    List<BookResponseDTO> searchBooksByMaxPrice(BigDecimal maxPrice);

    /**
     * Search books by minimum price.
     * 
     * @param minPrice minimum price (inclusive)
     * @return list of books with price >= minPrice
     */
    List<BookResponseDTO> searchBooksByMinPrice(BigDecimal minPrice);

    /**
     * Check if a book exists by ISBN.
     * 
     * @param isbn the ISBN to check
     * @return true if book exists, false otherwise
     */
    boolean bookExistsByIsbn(String isbn);

    /**
     * Convert Book entity to BookResponseDTO.
     * 
     * @param book the book entity
     * @return the book response DTO
     */
    BookResponseDTO convertToResponseDTO(Book book);

    /**
     * Convert BookRequestDTO to Book entity.
     * 
     * @param bookRequestDTO the book request DTO
     * @return the book entity
     */
    Book convertToEntity(BookRequestDTO bookRequestDTO);
}
