package com.cursordemo.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for book responses.
 * 
 * This DTO contains all the book information that will be returned to clients,
 * including audit fields like creation and update timestamps.
 * 
 * @author CursorDemo Team
 * @version 1.0.0
 */
public class BookResponseDto {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;

    /**
     * Default constructor.
     */
    public BookResponseDto() {
    }

    /**
     * Constructor with all fields.
     * 
     * @param id the book ID
     * @param title the book title
     * @param author the book author
     * @param isbn the book ISBN
     * @param price the book price
     * @param createdAt the creation timestamp
     * @param updatedAt the update timestamp
     * @param version the version number
     */
    public BookResponseDto(Long id, String title, String author, String isbn, 
                          BigDecimal price, LocalDateTime createdAt, 
                          LocalDateTime updatedAt, Long version) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "BookResponseDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", version=" + version +
                '}';
    }
}
