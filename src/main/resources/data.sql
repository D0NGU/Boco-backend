INSERT INTO categories(category) VALUES 
    ('verktøy'), 
    ('sportsutstyr');

INSERT INTO categories(CATEGORY, MAIN_CATEGORY) VALUES 
    ('skrutrekkere', 'verktøy'), 
    ('hammere', 'verktøy'), 
    ('stjerneskrutrekker', 'skrutrekkere');

INSERT INTO users (fname, lname, password, email, salt) 
    VALUES ('test','tester',CAST('08dab1fea88143614b4d449ba5ec067d' AS VARBINARY(64)),'t.est@tset.edu',CAST('9eb8eb1886c3184fa3f3d963c1578f40' AS VARBINARY(64)));