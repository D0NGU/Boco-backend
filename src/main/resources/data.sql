INSERT INTO categories(category) VALUES 
    ('elektronikk'),
    ('utstyr'),
    ('klær'),
    ('kjøretøy');

INSERT INTO categories(CATEGORY, MAIN_CATEGORY) VALUES 
    ('data', 'elektronikk'),
    ('foto og video', 'elektronikk'),
    ('hvitevarer', 'elektronikk'),
    ('verktøy', 'utstyr'),
    ('skrutrekkere', 'verktøy'), 
    ('hammere', 'verktøy'),
    ('dyreutstyr', 'utstyr'),
    ('hageutstyr', 'utstyr'),
    ('bil', 'kjøretøy'),
    ('båt', 'kjøretøy');


INSERT INTO user (id, fname, lname, password, email )
    values (1, 'per', 'persen', '$2a$10$OsIqAo6pFMHKjUzDRiOq9Os1L2tXfocby4mcspG2g2QrWqR6j7aBm', 'per@persen.com'); -- password = password

INSERT INTO user (id, fname, lname, password, email )
    values (2, 'guro', 'gurosen', '$2a$10$OsIqAo6pFMHKjUzDRiOq9Os1L2tXfocby4mcspG2g2QrWqR6j7aBm', 'guro@gurosen.com'); -- password = password

INSERT INTO products(title, description, address, price, unlisted, available_from, available_to, user_id, category)
    VALUES ('John Deere 7280R', 'Pent brukt traktor!', 'Myrangvegen 4, 2040 Kløfta', 450, false, '2022-04-11', '2022-06-20', 1, 'kjøretøy'),
    ('Valtra 34CX', 'Meget pent brukt traktor!!', 'Myrangvegen 6, 2040 Kløfta', 200, false, '2022-02-01', '2022-09-25', 1, 'kjøretøy'),
           ('John Deere 6090M','Stygt brukt traktor','Myrangvegen 2, 2040 Kløfta',500,false,'2022-03-01','2022-04-25',1,'kjøretøy'),
           ('John Deere 6R','Jævlig pent brukt traktor, basically ny ','Myrangvegen 1, 2040 Kløfta',1100,false,'2022-08-01','2022-12-25',1,'kjøretøy');