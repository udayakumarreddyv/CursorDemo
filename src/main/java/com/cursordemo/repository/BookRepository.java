package com.cursordemo.repository;

import com.cursordemo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Book entity.
 * 
 * This interface extends JpaRepository to provide basic CRUD operations
 * and includes custom query methods for searching books by various criteria.
 * 
 * @author Cursor Demo Team
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
     * Find books by title (case-insensitive partial match).
     * 
     * @param title the title to search for
     * @return list of books matching the title
     */
    List<Book> findByTitleContainingIgnoreCase(String title);

    /**
     * Find books by author (case-insensitive partial match).
     * 
     * @param author the author to search for
     * @return list of books matching the author
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);

    /**
     * Find books by title or author (case-insensitive partial match).
     * 
     * @param title the title to search for
     * @param author the author to search for
     * @return list of books matching either title or author
     */
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);

    /**
     * Find books within a price range.
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of books within the price range
     */
    @Query("SELECT b FROM Book b WHERE b.price BETWEEN :minPrice AND :maxPrice")
    List<Book> findByPriceRange(@Param("minPrice") java.math.BigDecimal minPrice, 
                                @Param("maxPrice") java.math.BigDecimal maxPrice);

    /**
     * Find books with price less than or equal to the specified price.
     * 
     * @param maxPrice the maximum price
     * @return list of books with price <= maxPrice
     */
    List<Book> findByPriceLessThanEqual(java.math.BigDecimal maxPrice);

    /**
     * Find books with price greater than or equal to the specified price.
     * 
     * @param minPrice the minimum price
     * @return list of books with price >= minPrice
     */
    List<Book> findByPriceGreaterThanEqual(java.math.BigDecimal minPrice);

    /**
     * Check if a book exists by ISBN.
     * 
     * @param isbn the ISBN to check
     * @return true if a book with the ISBN exists, false otherwise
     */
    boolean existsByIsbn(String isbn);

    /**
     * Custom search method that searches across title and author fields.
     * 
     * @param searchTerm the term to search for
     * @return list of books matching the search term
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Book> searchBooks(@Param("searchTerm") String searchTerm);
}
