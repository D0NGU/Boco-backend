-- Create tables

create table user (
                      id INTEGER NOT NULL AUTO_INCREMENT,
                      fname varchar(255),
                      lname varchar(255),
                      password varchar(255),
                      email varchar(255),
                      primary key (id)
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
    description VARCHAR(6000),
    address VARCHAR(255),
    price DECIMAL,
    unlisted BOOLEAN,
    available_from DATE,
    available_to DATE,
    user_id INTEGER NOT NULL,
    category VARCHAR(20),
    PRIMARY KEY (product_id)
    -- FULLTEXT(title, description) only in use on the mysql database
);

CREATE TABLE rentals(
    rental_id INTEGER NOT NULL AUTO_INCREMENT,
    date_from DATE NOT NULL,
    date_to DATE NOT NULL,
    accepted BOOLEAN NOT NULL,
    product_id INTEGER,
    user_id INTEGER,
    PRIMARY KEY (rental_id)
);

CREATE TABLE images(
    img_id INTEGER NOT NULL AUTO_INCREMENT,
    img_name VARCHAR(75),
    img_blob BLOB,
    product_id INTEGER,
    PRIMARY KEY (img_id)
);

-- Configure dependencies (FK/PK)
alter table user
    add constraint emailUnique unique (email);

ALTER TABLE reviews
    ADD CONSTRAINT FK_author
        FOREIGN KEY (author) 
        REFERENCES user(id);

ALTER TABLE reviews
    ADD CONSTRAINT FK_subject
        FOREIGN KEY (subject) 
        REFERENCES user(id) ON DELETE CASCADE;

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
        REFERENCES user(id) ON DELETE CASCADE;

ALTER TABLE products
    ADD CONSTRAINT FK_category
        FOREIGN KEY (category) 
        REFERENCES categories(category);

ALTER TABLE rentals
    ADD CONSTRAINT FK_renter
        FOREIGN KEY (user_id) 
        REFERENCES user(id) ON DELETE CASCADE;

ALTER TABLE rentals
    ADD CONSTRAINT FK_product
        FOREIGN KEY (product_id) 
        REFERENCES products(product_id) ON DELETE CASCADE ;

ALTER TABLE images
    ADD CONSTRAINT FK_product_img
        FOREIGN KEY (product_id)
        REFERENCES products(product_id) ON DELETE CASCADE;

-- Create index table for fulltext search. The table is updated in realtime.
CREATE ALIAS IF NOT EXISTS FT_INIT FOR "org.h2.fulltext.FullText.init";
CALL FT_INIT();
CALL FT_CREATE_INDEX('PUBLIC', 'PRODUCTS', 'TITLE,DESCRIPTION');