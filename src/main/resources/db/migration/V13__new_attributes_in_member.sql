ALTER TABLE members ADD date_of_birth VARCHAR(255) NULL;

ALTER TABLE members ADD member_description TEXT NULL;

ALTER TABLE members DROP COLUMN image;

ALTER TABLE band DROP COLUMN `path`;