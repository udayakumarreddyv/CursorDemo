-- Sample data for the Book Management API
-- This file contains initial data that will be loaded into the H2 database

INSERT INTO books (title, author, isbn, price, created_at, updated_at, version) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 12.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('To Kill a Mockingbird', 'Harper Lee', '9780446310789', 14.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('1984', 'George Orwell', '9780451524935', 11.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('Pride and Prejudice', 'Jane Austen', '9780141439518', 9.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('The Hobbit', 'J.R.R. Tolkien', '9780547928241', 15.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('The Catcher in the Rye', 'J.D. Salinger', '9780316769488', 13.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('Lord of the Flies', 'William Golding', '9780399501487', 10.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('Animal Farm', 'George Orwell', '9780451526342', 8.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('The Alchemist', 'Paulo Coelho', '9780062315007', 16.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('Brave New World', 'Aldous Huxley', '9780060850524', 12.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
