INSERT INTO categories(category) VALUES 
    ('elektronikk'),
    ('verktøy'),
    ('utstyr');

INSERT INTO categories(CATEGORY, MAIN_CATEGORY) VALUES 
    ('hvitevarer', 'elektronikk'),
    ('hammere', 'verktøy'),
    ('skrutrekkere', 'verktøy');

INSERT INTO categories(CATEGORY, MAIN_CATEGORY) VALUES
    ('stjerneskrutrekkere', 'skrutrekkere');

INSERT INTO user(fname, lname, password, email) VALUES
    ('test', 'tester', '$2a$10$OsIqAo6pFMHKjUzDRiOq9Os1L2tXfocby4mcspG2g2QrWqR6j7aBm', 't.est@tset.edu'), --password=password
    ('Satoru', 'Gojo', '$2a$10$OsIqAo6pFMHKjUzDRiOq9Os1L2tXfocby4mcspG2g2QrWqR6j7aBm', 'satoru.gojo@tokyo.high.edu'); --password=password

INSERT INTO products(title, description, address, price, unlisted, available_from, available_to, user_id, category, tlf)
    VALUES ('Dragon hunter crossbow', 'A dragonbane weapon requiring 65 Ranged to wield.', 'Gilenor', 600, false, '2022-07-12', '2022-12-24', 1, 'hvitevarer', '12345678');

INSERT INTO rentals (date_from, date_to, accepted, product_id, user_id) 
    VALUES ('2022-11-12', '2022-12-23', true, 1, 2);

INSERT INTO reviews(review_id, text, stars, owner, author, subject, date) VALUES 
    (1, 'Very powerful', 5, true, 1, 2, '2008-11-11 14:34:23'),
    (2, 'Nice guy', 4, false, 1, 2, '2022-01-04 11:54:22'),
    (3, 'Not sure how to feel about this guy', 1, true, 2, 1, '2017-10-03 03:56:11');