-- Create tables
CREATE TABLE users (
    user_id INTEGER NOT NULL AUTO_INCREMENT,
    fname VARCHAR(75)  NOT NULL,
    lname VARCHAR(75) NOT NULL,
    email VARCHAR(75) NOT NULL UNIQUE ,
    salt VARBINARY(64) NOT NULL,
    password VARBINARY(64) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE reviews (
    review_id INTEGER NOT NULL AUTO_INCREMENT,
    text VARCHAR(255),
    stars INTEGER NOT NULL,
    owner boolean NOT NULL,
    author INTEGER NOT NULL,
    subject INTEGER NOT NULL,
    PRIMARY KEY (review_id)
);

CREATE TABLE categories (
    category VARCHAR(20) NOT NULL,
    main_category VARCHAR(20),
    PRIMARY KEY (category)
);

CREATE TABLE products (
    product_id INTEGER NOT NULL AUTO_INCREMENT,
    title VARCHAR(75) NOT NULL,
    description VARCHAR(MAX),
    address VARCHAR(255),
    price DECIMAL,
    unlisted BOOLEAN,
    available_from DATE,
    available_to DATE,
    user_id INTEGER NOT NULL,
    category VARCHAR(20),
    PRIMARY KEY (product_id)
);

CREATE TABLE rentals(
    rental_id INTEGER NOT NULL AUTO_INCREMENT,
    date_from DATE NOT NULL,
    date_to DATE NOT NULL,
    product_id INTEGER,
    user_id INTEGER,
    PRIMARY KEY (rental_id)
);

-- Configure dependencies (FK/PK)
ALTER TABLE reviews
    ADD CONSTRAINT FK_author
        FOREIGN KEY (author) 
        REFERENCES users(user_id);

ALTER TABLE reviews
    ADD CONSTRAINT FK_subject
        FOREIGN KEY (subject) 
        REFERENCES users(user_id) ON DELETE CASCADE;

ALTER TABLE reviews
    ADD CONSTRAINT valid_stars 
    CHECK (stars>=1 AND stars<=5);

ALTER TABLE categories
    ADD CONSTRAINT FK_main_category
        FOREIGN KEY (main_category) 
        REFERENCES categories(category);

ALTER TABLE products
    ADD CONSTRAINT FK_owner
        FOREIGN KEY (user_id) 
        REFERENCES users(user_id) ON DELETE CASCADE;

ALTER TABLE products
    ADD CONSTRAINT FK_category
        FOREIGN KEY (category) 
        REFERENCES categories(category);

ALTER TABLE rentals
    ADD CONSTRAINT FK_renter
        FOREIGN KEY (user_id) 
        REFERENCES users(user_id) ON DELETE CASCADE;

ALTER TABLE rentals
    ADD CONSTRAINT FK_product
        FOREIGN KEY (product_id) 
        REFERENCES products(product_id) ON DELETE CASCADE;
