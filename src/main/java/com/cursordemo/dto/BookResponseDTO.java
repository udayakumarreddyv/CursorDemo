package com.cursordemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Book response data.
 * 
 * This DTO is used to return book information in API responses,
 * including all book details and metadata.
 */
@Schema(description = "Book response data transfer object")
public class BookResponseDTO {

    @Schema(description = "Unique identifier for the book", example = "1")
    private Long id;

    @Schema(description = "Book title", example = "The Great Gatsby")
    private String title;

    @Schema(description = "Book author", example = "F. Scott Fitzgerald")
    private String author;

    @Schema(description = "International Standard Book Number", example = "978-0743273565")
    private String isbn;

    @Schema(description = "Book price", example = "29.99")
    private BigDecimal price;

    @Schema(description = "Timestamp when the book was created", example = "2023-12-01T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the book was last updated", example = "2023-12-01T10:30:00")
    private LocalDateTime updatedAt;

    // Default constructor
    public BookResponseDTO() {}

    // Constructor with all fields
    public BookResponseDTO(Long id, String title, String author, String isbn, 
                          BigDecimal price, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "BookResponseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
