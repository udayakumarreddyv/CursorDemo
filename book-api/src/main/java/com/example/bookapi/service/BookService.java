package com.example.bookapi.service;

import com.example.bookapi.dto.BookCreateRequest;
import com.example.bookapi.dto.BookResponse;
import com.example.bookapi.dto.BookUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse create(BookCreateRequest request);
    BookResponse getById(Long id);
    BookResponse update(Long id, BookUpdateRequest request);
    void delete(Long id);
    Page<BookResponse> search(String title, String author, Integer publishedYear, Pageable pageable);
}


