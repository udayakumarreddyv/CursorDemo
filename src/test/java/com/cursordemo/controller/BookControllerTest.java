package com.cursordemo.controller;

import com.cursordemo.dto.BookRequestDTO;
import com.cursordemo.dto.BookResponseDTO;
import com.cursordemo.exception.BookNotFoundException;
import com.cursordemo.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for BookController.
 * 
 * Tests all REST endpoints with proper authentication and validation.
 */
@ExtendWith(MockitoExtension.class)
@WithMockUser(username = "admin", roles = {"ADMIN"})
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private BookRequestDTO bookRequestDTO;
    private BookResponseDTO bookResponseDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        objectMapper = new ObjectMapper();

        // Setup test data
        bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setTitle("Test Book");
        bookRequestDTO.setAuthor("Test Author");
        bookRequestDTO.setIsbn("978-1234567890");
        bookRequestDTO.setPrice(new BigDecimal("29.99"));

        bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setId(1L);
        bookResponseDTO.setTitle("Test Book");
        bookResponseDTO.setAuthor("Test Author");
        bookResponseDTO.setIsbn("978-1234567890");
        bookResponseDTO.setPrice(new BigDecimal("29.99"));
        bookResponseDTO.setCreatedAt(LocalDateTime.now());
        bookResponseDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createBook_Success() throws Exception {
        when(bookService.createBook(any(BookRequestDTO.class))).thenReturn(bookResponseDTO);

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"))
                .andExpect(jsonPath("$.isbn").value("978-1234567890"))
                .andExpect(jsonPath("$.price").value(29.99));

        verify(bookService, times(1)).createBook(any(BookRequestDTO.class));
    }

    @Test
    void createBook_ValidationError() throws Exception {
        bookRequestDTO.setTitle(""); // Invalid title

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequestDTO)))
                .andExpect(status().isBadRequest());

        verify(bookService, never()).createBook(any(BookRequestDTO.class));
    }

    @Test
    void getBookById_Success() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(bookResponseDTO);

        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Book"));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void getBookById_NotFound() throws Exception {
        when(bookService.getBookById(999L)).thenThrow(new BookNotFoundException("Book not found"));

        mockMvc.perform(get("/api/v1/books/999"))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).getBookById(999L);
    }

    @Test
    void getBookByIsbn_Success() throws Exception {
        when(bookService.getBookByIsbn("978-1234567890")).thenReturn(bookResponseDTO);

        mockMvc.perform(get("/api/v1/books/isbn/978-1234567890"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("978-1234567890"));

        verify(bookService, times(1)).getBookByIsbn("978-1234567890");
    }

    @Test
    void getAllBooks_Success() throws Exception {
        List<BookResponseDTO> books = Arrays.asList(bookResponseDTO);
        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Book"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void updateBook_Success() throws Exception {
        when(bookService.updateBook(eq(1L), any(BookRequestDTO.class))).thenReturn(bookResponseDTO);

        mockMvc.perform(put("/api/v1/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(bookService, times(1)).updateBook(eq(1L), any(BookRequestDTO.class));
    }

    @Test
    void deleteBook_Success() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    void searchBooksByTitle_Success() throws Exception {
        List<BookResponseDTO> books = Arrays.asList(bookResponseDTO);
        when(bookService.searchBooksByTitle("Test")).thenReturn(books);

        mockMvc.perform(get("/api/v1/books/search/title")
                .param("title", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"));

        verify(bookService, times(1)).searchBooksByTitle("Test");
    }

    @Test
    void searchBooksByAuthor_Success() throws Exception {
        List<BookResponseDTO> books = Arrays.asList(bookResponseDTO);
        when(bookService.searchBooksByAuthor("Test Author")).thenReturn(books);

        mockMvc.perform(get("/api/v1/books/search/author")
                .param("author", "Test Author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value("Test Author"));

        verify(bookService, times(1)).searchBooksByAuthor("Test Author");
    }

    @Test
    void searchBooksByTitleOrAuthor_Success() throws Exception {
        List<BookResponseDTO> books = Arrays.asList(bookResponseDTO);
        when(bookService.searchBooksByTitleOrAuthor("Test", "Author")).thenReturn(books);

        mockMvc.perform(get("/api/v1/books/search")
                .param("title", "Test")
                .param("author", "Author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"));

        verify(bookService, times(1)).searchBooksByTitleOrAuthor("Test", "Author");
    }

    @Test
    void searchBooksByPriceRange_Success() throws Exception {
        List<BookResponseDTO> books = Arrays.asList(bookResponseDTO);
        when(bookService.searchBooksByPriceRange(any(BigDecimal.class), any(BigDecimal.class))).thenReturn(books);

        mockMvc.perform(get("/api/v1/books/search/price-range")
                .param("minPrice", "10.00")
                .param("maxPrice", "50.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price").value(29.99));

        verify(bookService, times(1)).searchBooksByPriceRange(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void searchBooksByMaxPrice_Success() throws Exception {
        List<BookResponseDTO> books = Arrays.asList(bookResponseDTO);
        when(bookService.searchBooksByMaxPrice(any(BigDecimal.class))).thenReturn(books);

        mockMvc.perform(get("/api/v1/books/search/price-max")
                .param("maxPrice", "50.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price").value(29.99));

        verify(bookService, times(1)).searchBooksByMaxPrice(any(BigDecimal.class));
    }

    @Test
    void searchBooksByMinPrice_Success() throws Exception {
        List<BookResponseDTO> books = Arrays.asList(bookResponseDTO);
        when(bookService.searchBooksByMinPrice(any(BigDecimal.class))).thenReturn(books);

        mockMvc.perform(get("/api/v1/books/search/price-min")
                .param("minPrice", "10.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price").value(29.99));

        verify(bookService, times(1)).searchBooksByMinPrice(any(BigDecimal.class));
    }

    @Test
    void bookExistsByIsbn_Success() throws Exception {
        when(bookService.bookExistsByIsbn("978-1234567890")).thenReturn(true);

        mockMvc.perform(get("/api/v1/books/exists/978-1234567890"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(bookService, times(1)).bookExistsByIsbn("978-1234567890");
    }
}
