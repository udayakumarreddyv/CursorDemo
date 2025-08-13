package com.example.bookapi.repository;

import com.example.bookapi.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);

    Page<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(String title, String author, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndPublishedYear(String title, String author, Integer publishedYear, Pageable pageable);
}


