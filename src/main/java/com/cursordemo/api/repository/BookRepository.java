package com.cursordemo.api.repository;

import com.cursordemo.api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Book entity.
 * 
 * This interface provides data access methods for the Book entity,
 * including custom search functionality.
 * 
 * @author CursorDemo Team
 * @version 1.0.0
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Find a book by its ISBN.
     * 
     * @param isbn the ISBN to search for
     * @return Optional containing the book if found
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Check if a book exists by ISBN.
     * 
     * @param isbn the ISBN to check
     * @return true if a book with the given ISBN exists
     */
    boolean existsByIsbn(String isbn);

    /**
     * Find books by title containing the given keyword (case-insensitive).
     * 
     * @param title the title keyword to search for
     * @return list of books matching the title
     */
    List<Book> findByTitleContainingIgnoreCase(String title);

    /**
     * Find books by author containing the given keyword (case-insensitive).
     * 
     * @param author the author keyword to search for
     * @return list of books matching the author
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);

    /**
     * Find books by title or author containing the given keyword (case-insensitive).
     * 
     * @param keyword the keyword to search for in title or author
     * @return list of books matching the keyword
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> findByTitleOrAuthorContainingIgnoreCase(@Param("keyword") String keyword);

    /**
     * Find books with price less than or equal to the given price.
     * 
     * @param maxPrice the maximum price
     * @return list of books with price <= maxPrice
     */
    List<Book> findByPriceLessThanEqual(java.math.BigDecimal maxPrice);

    /**
     * Find books with price greater than or equal to the given price.
     * 
     * @param minPrice the minimum price
     * @return list of books with price >= minPrice
     */
    List<Book> findByPriceGreaterThanEqual(java.math.BigDecimal minPrice);

    /**
     * Find books with price between minPrice and maxPrice (inclusive).
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of books with price between minPrice and maxPrice
     */
    List<Book> findByPriceBetween(java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice);
}
