package com.example.bookapi.controller;

import com.example.bookapi.dto.BookCreateRequest;
import com.example.bookapi.dto.BookResponse;
import com.example.bookapi.dto.BookUpdateRequest;
import com.example.bookapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Create a new book")
    @PostMapping
    public ResponseEntity<BookResponse> create(@Validated @RequestBody BookCreateRequest request) {
        return new ResponseEntity<>(bookService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a book by id")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @Operation(summary = "Update a book by id")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @Validated @RequestBody BookUpdateRequest request) {
        return ResponseEntity.ok(bookService.update(id, request));
    }

    @Operation(summary = "Delete a book by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search books")
    @GetMapping
    public ResponseEntity<Page<BookResponse>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer publishedYear,
            Pageable pageable) {
        return ResponseEntity.ok(bookService.search(title, author, publishedYear, pageable));
    }
}


