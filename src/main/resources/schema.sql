-- Create tables

create table user (
    id INTEGER NOT NULL AUTO_INCREMENT,
    fname VARCHAR(75),
    lname VARCHAR(75),
    description varchar(6000),
    password VARCHAR(255),
    email VARCHAR(120),
    signup DATE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE reviews (
    review_id INTEGER NOT NULL AUTO_INCREMENT,
    text VARCHAR(255),
    stars INTEGER NOT NULL,
    owner BOOLEAN NOT NULL,
    author INTEGER NOT NULL,
    subject INTEGER NOT NULL,
    date DATETIME NOT NULL,
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
    img_blob MEDIUMBLOB,
    img_data VARCHAR(150),
    product_id INTEGER,
    PRIMARY KEY (img_id)
);

CREATE TABLE alerts(
    alert_id INTEGER NOT NULL AUTO_INCREMENT,
    description VARCHAR(6000) NOT NULL,
    alert_date DATE NOT NULL,
    has_seen boolean NOT NULL,
    optional_id INTEGER,
    user_id INTEGER NOT NULL,
    PRIMARY KEY (alert_id)
);

CREATE TABLE contact_forms (
    contact_form_id INTEGER NOT NULL AUTO_INCREMENT,
    fname VARCHAR(75),
    lname VARCHAR(75),
    email VARCHAR(120) NOT NULL,
    comment VARCHAR(6000) NOT NULL,
    user_id INTEGER NOT NULL,
    PRIMARY KEY (contact_form_id)
);

-- Configure dependencies (FK/PK)
ALTER TABLE user
    ADD CONSTRAINT emailUnique UNIQUE (email);

ALTER TABLE products
    ADD CONSTRAINT titleUnique UNIQUE (title);

ALTER TABLE reviews
    ADD CONSTRAINT FK_author
        FOREIGN KEY (author)
        REFERENCES user(id) ON DELETE CASCADE;

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

ALTER TABLE products
    ADD CONSTRAINT title_unique
    UNIQUE (title);

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

ALTER TABLE alerts
    ADD CONSTRAINT FK_target
        FOREIGN KEY (user_id)
        REFERENCES user(id) ON DELETE CASCADE;

ALTER TABLE contact_forms
    ADD CONSTRAINT FK_user
        FOREIGN KEY (user_id)
        REFERENCES user(id) ON DELETE CASCADE;

-- Create index table for fulltext search. The table is updated in realtime.
CREATE ALIAS IF NOT EXISTS FT_INIT FOR "org.h2.fulltext.FullText.init";
CALL FT_INIT();
CALL FT_CREATE_INDEX('PUBLIC', 'PRODUCTS', 'TITLE,DESCRIPTION');