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

INSERT INTO users (fname, lname, password, email, salt) 
    VALUES ('test','tester',CAST('08dab1fea88143614b4d449ba5ec067d' AS VARBINARY(64)),'t.est@tset.edu',CAST('9eb8eb1886c3184fa3f3d963c1578f40' AS VARBINARY(64))); --password=test123

INSERT INTO products(title, description, address, price, unlisted, user_id, category) 
    VALUES ('John Deere 7280R', 'Pent brukt traktor!', 'Myrangvegen 4, 2040 Kløfta', 450, false, 1, 'kjøretøy'),
    ('Valtra 34CX', 'Meget pent brukt traktor!!', 'Myrangvegen 6, 2040 Kløfta', 200, false, 1, 'kjøretøy');

-- Create index table for fulltext search. The index is updated in realtime.
CREATE ALIAS IF NOT EXISTS FT_INIT FOR "org.h2.fulltext.FullText.init";
CALL FT_INIT();
CALL FT_CREATE_INDEX('PUBLIC', 'PRODUCTS', 'TITLE,DESCRIPTION');