ALTER TABLE band DROP COLUMN image;

ALTER TABLE albums MODIFY image VARCHAR(100);

ALTER TABLE members MODIFY image VARCHAR(100);

ALTER TABLE product MODIFY image VARCHAR(100);