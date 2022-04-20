CREATE TABLE user (
    user_id int(11) NOT NULL AUTO_INCREMENT,
    fname varchar(255)  NOT NULL,
    lname varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    salt varbinary(64) NOT NULL,
    password varbinary(64) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE review (
    review_id int(11) not null AUTO_INCREMENT,
    text varchar(255),
    stars int(1) not null,
    owner boolean not null,
    author int(11) not null,
    subject int(11) not null,
    PRIMARY KEY (review_id)
);

ALTER TABLE review
    ADD CONSTRAINT FK_author
        FOREIGN KEY (author) REFERENCES user(user_id);

ALTER TABLE review
    ADD CONSTRAINT FK_subject
        FOREIGN KEY (subject) REFERENCES user(user_id);

ALTER TABLE review
    ADD CONSTRAINT valid_stars CHECK (stars>=1 AND stars<=5);

CREATE TABLE category (
    category varchar(255) not null,
    main_category varchar(255),
    PRIMARY KEY (category)
);

ALTER TABLE category
    ADD CONSTRAINT FK_main_category
        FOREIGN KEY (main_category) REFERENCES category(category);

CREATE TABLE product (
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

ALTER TABLE product
    ADD CONSTRAINT FK_owner
        FOREIGN KEY (user_id) REFERENCES user(user_id);

ALTER TABLE product
    ADD CONSTRAINT FK_category
        FOREIGN KEY (category) REFERENCES category(category);

CREATE TABLE rental(
    rental_id int(11) not null AUTO_INCREMENT,
    date_from date not null,
    date_to date not null,
    product_id int(11),
    user_id int(11),
    PRIMARY KEY (rental_id)
);

ALTER TABLE rental
    ADD CONSTRAINT FK_renter
        FOREIGN KEY (user_id) REFERENCES user(user_id);

ALTER TABLE rental
    ADD CONSTRAINT FK_product
        FOREIGN KEY (product_id) REFERENCES product(product_id);
