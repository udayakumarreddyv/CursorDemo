-- Sample data for the books table
-- This file will be executed automatically when the application starts

INSERT INTO books (title, author, isbn, price, created_at, updated_at) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', '978-0743273565', 29.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('To Kill a Mockingbird', 'Harper Lee', '978-0446310789', 24.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('1984', 'George Orwell', '978-0451524935', 19.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Pride and Prejudice', 'Jane Austen', '978-0141439518', 15.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('The Hobbit', 'J.R.R. Tolkien', '978-0547928241', 34.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('The Catcher in the Rye', 'J.D. Salinger', '978-0316769488', 22.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Lord of the Flies', 'William Golding', '978-0399501487', 18.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Animal Farm', 'George Orwell', '978-0451526342', 16.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('The Alchemist', 'Paulo Coelho', '978-0062315007', 27.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Brave New World', 'Aldous Huxley', '978-0060850524', 25.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
