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


INSERT INTO user (id, name, username, password)
values (1, 'oskar', 'oskareid@stud.ntnu.no', '$2a$10$OsIqAo6pFMHKjUzDRiOq9Os1L2tXfocby4mcspG2g2QrWqR6j7aBm');

INSERT INTO role (id, name)
values (1, 'ROLE_USER');

INSERT INTO role (id, name)
values (2, 'ROLE_ADMIN');

INSERT INTO user_roles (user_id, roles_id)
values (1, 1);

INSERT INTO user_roles (user_id, roles_id)
values (1, 2);


INSERT INTO products(title, description, address, price, unlisted, available_from, available_to, user_id, category)
    VALUES ('John Deere 7280R', 'Pent brukt traktor!', 'Myrangvegen 4, 2040 Kløfta', 450, false, '2022-04-11', '2022-06-20', 1, 'kjøretøy'),
    ('Valtra 34CX', 'Meget pent brukt traktor!!', 'Myrangvegen 6, 2040 Kløfta', 200, false, '2022-02-01', '2022-09-25', 1, 'kjøretøy');