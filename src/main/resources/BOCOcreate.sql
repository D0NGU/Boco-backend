CREATE TABLE users (
    user_id int(11) NOT NULL AUTO_INCREMENT,
    fname varchar(255)  NOT NULL,
    lname varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    salt varbinary(64) NOT NULL,
    password varbinary(64) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE reviews (
    review_id int(11) not null AUTO_INCREMENT,
    text varchar(255),
    stars int(1) not null,
    owner boolean not null,
    author int(11) not null,
    subject int(11) not null,
    PRIMARY KEY (review_id)
);

ALTER TABLE reviews
    ADD CONSTRAINT FK_author
        FOREIGN KEY (author) REFERENCES users(user_id);

ALTER TABLE reviews
    ADD CONSTRAINT FK_subject
        FOREIGN KEY (subject) REFERENCES users(user_id);

ALTER TABLE reviews
    ADD CONSTRAINT valid_stars CHECK (stars>=1 AND stars<=5);

CREATE TABLE categories (
    category varchar(255) not null,
    main_category varchar(255),
    PRIMARY KEY (category)
);

ALTER TABLE categories
    ADD CONSTRAINT FK_main_category
        FOREIGN KEY (main_category) REFERENCES categories(category);

CREATE TABLE products (
    product_id int(11) not null AUTO_INCREMENT,
    name varchar(255) not null,
    description varchar(255),
    address varchar(255),
    price double,
    unlisted boolean,
    user_id int(11),
    category varchar(255),
    PRIMARY KEY (product_id)
);

ALTER TABLE products
    ADD CONSTRAINT FK_owner
        FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE products
    ADD CONSTRAINT FK_category
        FOREIGN KEY (category) REFERENCES categories(category);

CREATE TABLE rentals(
    rental_id int(11) not null AUTO_INCREMENT,
    date_from date not null,
    date_to date not null,
    product_id int(11),
    user_id int(11),
    PRIMARY KEY (rental_id)
);

ALTER TABLE rentals
    ADD CONSTRAINT FK_renter
        FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE rentals
    ADD CONSTRAINT FK_product
        FOREIGN KEY (product_id) REFERENCES products(product_id);
