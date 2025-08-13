package com.example.bookapi.service;

import com.example.bookapi.dto.BookCreateRequest;
import com.example.bookapi.dto.BookResponse;
import com.example.bookapi.dto.BookUpdateRequest;
import com.example.bookapi.entity.Book;
import com.example.bookapi.exception.BadRequestException;
import com.example.bookapi.exception.ResourceNotFoundException;
import com.example.bookapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookResponse create(BookCreateRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BadRequestException("ISBN already exists");
        }
        Book entity = toEntity(request);
        return toResponse(bookRepository.save(entity));
    }

    @Override
    public BookResponse getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return toResponse(book);
    }

    @Override
    public BookResponse update(Long id, BookUpdateRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BadRequestException("ISBN already exists");
        }
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublishedYear(request.getPublishedYear());
        return toResponse(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        bookRepository.delete(book);
    }

    @Override
    public Page<BookResponse> search(String title, String author, Integer publishedYear, Pageable pageable) {
        String titleFilter = title == null ? "" : title;
        String authorFilter = author == null ? "" : author;
        Page<Book> page;
        if (publishedYear != null) {
            page = bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndPublishedYear(titleFilter, authorFilter, publishedYear, pageable);
        } else {
            page = bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(titleFilter, authorFilter, pageable);
        }
        return page.map(this::toResponse);
    }

    private Book toEntity(BookCreateRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublishedYear(request.getPublishedYear());
        return book;
    }

    private BookResponse toResponse(Book entity) {
        BookResponse r = new BookResponse();
        r.setId(entity.getId());
        r.setTitle(entity.getTitle());
        r.setAuthor(entity.getAuthor());
        r.setIsbn(entity.getIsbn());
        r.setPublishedYear(entity.getPublishedYear());
        r.setCreatedAt(entity.getCreatedAt());
        r.setUpdatedAt(entity.getUpdatedAt());
        return r;
    }
}


