package com.example.bookapi.service;

import com.example.bookapi.dto.BookCreateRequest;
import com.example.bookapi.entity.Book;
import com.example.bookapi.exception.BadRequestException;
import com.example.bookapi.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    private BookRepository bookRepository;
    private BookServiceImpl service;

    @BeforeEach
    void setup() {
        bookRepository = Mockito.mock(BookRepository.class);
        service = new BookServiceImpl(bookRepository);
    }

    @Test
    void create_shouldPersist_whenIsbnUnique() {
        when(bookRepository.existsByIsbn("X")).thenReturn(false);
        Book saved = new Book();
        saved.setId(1L);
        saved.setTitle("T");
        saved.setAuthor("A");
        saved.setIsbn("X");
        when(bookRepository.save(any(Book.class))).thenReturn(saved);

        BookCreateRequest req = new BookCreateRequest();
        req.setTitle("T");
        req.setAuthor("A");
        req.setIsbn("X");

        assertEquals(1L, service.create(req).getId());

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        assertEquals("T", captor.getValue().getTitle());
    }

    @Test
    void create_shouldThrow_whenIsbnExists() {
        when(bookRepository.existsByIsbn("X")).thenReturn(true);
        BookCreateRequest req = new BookCreateRequest();
        req.setTitle("T");
        req.setAuthor("A");
        req.setIsbn("X");
        assertThrows(BadRequestException.class, () -> service.create(req));
    }

    @Test
    void search_shouldCallRepository() {
        when(bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(eq(""), eq(""), any()))
                .thenReturn(new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0));
        service.search(null, null, null, PageRequest.of(0, 10));
        verify(bookRepository).findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(eq(""), eq(""), any());
    }
}


