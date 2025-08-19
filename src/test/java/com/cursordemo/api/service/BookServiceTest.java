package com.cursordemo.api.service;

import com.cursordemo.api.dto.BookRequestDto;
import com.cursordemo.api.dto.BookResponseDto;
import com.cursordemo.api.entity.Book;
import com.cursordemo.api.exception.BookNotFoundException;
import com.cursordemo.api.exception.DuplicateIsbnException;
import com.cursordemo.api.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for BookService.
 * 
 * @author CursorDemo Team
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookRequestDto bookRequestDto;
    private Book book;
    private BookResponseDto expectedResponseDto;

    @BeforeEach
    void setUp() {
        bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("Test Book");
        bookRequestDto.setAuthor("Test Author");
        bookRequestDto.setIsbn("1234567890123");
        bookRequestDto.setPrice(new BigDecimal("29.99"));

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890123");
        book.setPrice(new BigDecimal("29.99"));
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        book.setVersion(0L);

        expectedResponseDto = new BookResponseDto();
        expectedResponseDto.setId(1L);
        expectedResponseDto.setTitle("Test Book");
        expectedResponseDto.setAuthor("Test Author");
        expectedResponseDto.setIsbn("1234567890123");
        expectedResponseDto.setPrice(new BigDecimal("29.99"));
        expectedResponseDto.setCreatedAt(book.getCreatedAt());
        expectedResponseDto.setUpdatedAt(book.getUpdatedAt());
        expectedResponseDto.setVersion(0L);
    }

    @Test
    void createBook_Success() {
        // Given
        when(bookRepository.existsByIsbn(bookRequestDto.getIsbn())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        BookResponseDto result = bookService.createBook(bookRequestDto);

        // Then
        assertNotNull(result);
        assertEquals(expectedResponseDto.getId(), result.getId());
        assertEquals(expectedResponseDto.getTitle(), result.getTitle());
        assertEquals(expectedResponseDto.getAuthor(), result.getAuthor());
        assertEquals(expectedResponseDto.getIsbn(), result.getIsbn());
        assertEquals(expectedResponseDto.getPrice(), result.getPrice());

        verify(bookRepository).existsByIsbn(bookRequestDto.getIsbn());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void createBook_DuplicateIsbn_ThrowsException() {
        // Given
        when(bookRepository.existsByIsbn(bookRequestDto.getIsbn())).thenReturn(true);

        // When & Then
        assertThrows(DuplicateIsbnException.class, () -> bookService.createBook(bookRequestDto));
        verify(bookRepository).existsByIsbn(bookRequestDto.getIsbn());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void getBookById_Success() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // When
        BookResponseDto result = bookService.getBookById(1L);

        // Then
        assertNotNull(result);
        assertEquals(expectedResponseDto.getId(), result.getId());
        assertEquals(expectedResponseDto.getTitle(), result.getTitle());
        verify(bookRepository).findById(1L);
    }

    @Test
    void getBookById_NotFound_ThrowsException() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository).findById(1L);
    }

    @Test
    void getBookByIsbn_Success() {
        // Given
        when(bookRepository.findByIsbn("1234567890123")).thenReturn(Optional.of(book));

        // When
        BookResponseDto result = bookService.getBookByIsbn("1234567890123");

        // Then
        assertNotNull(result);
        assertEquals(expectedResponseDto.getIsbn(), result.getIsbn());
        verify(bookRepository).findByIsbn("1234567890123");
    }

    @Test
    void getBookByIsbn_NotFound_ThrowsException() {
        // Given
        when(bookRepository.findByIsbn("1234567890123")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookNotFoundException.class, () -> bookService.getBookByIsbn("1234567890123"));
        verify(bookRepository).findByIsbn("1234567890123");
    }

    @Test
    void getAllBooks_Success() {
        // Given
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findAll()).thenReturn(books);

        // When
        List<BookResponseDto> result = bookService.getAllBooks();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedResponseDto.getId(), result.get(0).getId());
        verify(bookRepository).findAll();
    }

    @Test
    void updateBook_Success() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        BookResponseDto result = bookService.updateBook(1L, bookRequestDto);

        // Then
        assertNotNull(result);
        assertEquals(expectedResponseDto.getId(), result.getId());
        verify(bookRepository).findById(1L);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void updateBook_NotFound_ThrowsException() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1L, bookRequestDto));
        verify(bookRepository).findById(1L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook_Success() {
        // Given
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        // When
        bookService.deleteBook(1L);

        // Then
        verify(bookRepository).existsById(1L);
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void deleteBook_NotFound_ThrowsException() {
        // Given
        when(bookRepository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository).existsById(1L);
        verify(bookRepository, never()).deleteById(any());
    }

    @Test
    void searchBooksByTitle_Success() {
        // Given
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findByTitleContainingIgnoreCase("Test")).thenReturn(books);

        // When
        List<BookResponseDto> result = bookService.searchBooksByTitle("Test");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedResponseDto.getTitle(), result.get(0).getTitle());
        verify(bookRepository).findByTitleContainingIgnoreCase("Test");
    }

    @Test
    void searchBooksByAuthor_Success() {
        // Given
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findByAuthorContainingIgnoreCase("Test")).thenReturn(books);

        // When
        List<BookResponseDto> result = bookService.searchBooksByAuthor("Test");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedResponseDto.getAuthor(), result.get(0).getAuthor());
        verify(bookRepository).findByAuthorContainingIgnoreCase("Test");
    }

    @Test
    void searchBooks_Success() {
        // Given
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findByTitleOrAuthorContainingIgnoreCase("Test")).thenReturn(books);

        // When
        List<BookResponseDto> result = bookService.searchBooks("Test");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository).findByTitleOrAuthorContainingIgnoreCase("Test");
    }
}
