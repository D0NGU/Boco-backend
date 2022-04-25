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

INSERT INTO users (fname, lname, password, email, salt) VALUES 
    ('test','tester',CAST('08dab1fea88143614b4d449ba5ec067d' AS VARBINARY(64)),'t.est@tset.edu',CAST('9eb8eb1886c3184fa3f3d963c1578f40' AS VARBINARY(64))), --password=test123
    ('Satoru','Gojo',CAST('b6470d23c3a3e99ff8456999d55eb2d2' AS VARBINARY(64)),'satoru.gojo@tokyo.high.edu',CAST('d52e076e754510dce2bcd422cadb3d77' AS VARBINARY(64))); --password=jujutsulimitless

INSERT INTO products(title, description, address, price, unlisted, available_from, available_to, user_id, category)
    VALUES ('Dragon hunter crossbow', 'A dragonbane weapon requiring 65 Ranged to wield.', 'Gilenor', 600, false, '2022-07-12', '2022-12-24', 1, 'hvitevarer');