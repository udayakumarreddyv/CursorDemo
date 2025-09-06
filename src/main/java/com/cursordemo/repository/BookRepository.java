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
 * Provides data access methods for Book operations including
 * CRUD operations and custom search queries.
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
     * @return true if book exists, false otherwise
     */
    boolean existsByIsbn(String isbn);

    /**
     * Find books by author name (case-insensitive).
     * 
     * @param author the author name to search for
     * @return list of books by the specified author
     */
    List<Book> findByAuthorIgnoreCaseContaining(String author);

    /**
     * Find books by title (case-insensitive).
     * 
     * @param title the title to search for
     * @return list of books with matching title
     */
    List<Book> findByTitleIgnoreCaseContaining(String title);

    /**
     * Find books by title or author (case-insensitive).
     * 
     * @param title the title to search for
     * @param author the author to search for
     * @return list of books matching either title or author
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')) " +
           "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<Book> findByTitleOrAuthorContaining(@Param("title") String title, @Param("author") String author);

    /**
     * Find books within a price range.
     * 
     * @param minPrice minimum price (inclusive)
     * @param maxPrice maximum price (inclusive)
     * @return list of books within the price range
     */
    @Query("SELECT b FROM Book b WHERE b.price BETWEEN :minPrice AND :maxPrice ORDER BY b.price")
    List<Book> findByPriceBetween(@Param("minPrice") java.math.BigDecimal minPrice, 
                                  @Param("maxPrice") java.math.BigDecimal maxPrice);

    /**
     * Find books with price less than or equal to the specified price.
     * 
     * @param maxPrice maximum price (inclusive)
     * @return list of books with price <= maxPrice
     */
    List<Book> findByPriceLessThanEqualOrderByPrice(java.math.BigDecimal maxPrice);

    /**
     * Find books with price greater than or equal to the specified price.
     * 
     * @param minPrice minimum price (inclusive)
     * @return list of books with price >= minPrice
     */
    List<Book> findByPriceGreaterThanEqualOrderByPrice(java.math.BigDecimal minPrice);
}
