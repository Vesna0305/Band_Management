ALTER TABLE band ADD image VARCHAR(100) NULL;

ALTER TABLE albums MODIFY image VARCHAR(100) NOT NULL;

ALTER TABLE members MODIFY image VARCHAR(100) NOT NULL;

ALTER TABLE product MODIFY image VARCHAR(100) NOT NULL;