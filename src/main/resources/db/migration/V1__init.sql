CREATE TABLE albums (
  id char(36) NOT NULL,
   album_name VARCHAR(30) NOT NULL,
   year BIGINT NOT NULL,
   image VARCHAR(50) NOT NULL,
   band_id char(36) NULL,
   CONSTRAINT pk_albums PRIMARY KEY (id)
);

CREATE TABLE band (
  id char(36) NOT NULL,
   band_name VARCHAR(30) NOT NULL,
   year BIGINT NOT NULL,
   genre_id char(36) NULL,
   CONSTRAINT pk_band PRIMARY KEY (id)
);

CREATE TABLE categories (
  id char(36) NOT NULL,
   category_name VARCHAR(255) NULL,
   CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE genre (
  id char(36) NOT NULL,
   genre_name VARCHAR(15) NOT NULL,
   CONSTRAINT pk_genre PRIMARY KEY (id)
);

CREATE TABLE member_position (
  member_id char(36) NOT NULL,
   position_id char(36) NOT NULL
);

CREATE TABLE members (
  id char(36) NOT NULL,
   first_name VARCHAR(30) NOT NULL,
   last_name VARCHAR(30) NOT NULL,
   image VARCHAR(4) NOT NULL,
   band_id char(36) NULL,
   CONSTRAINT pk_members PRIMARY KEY (id)
);

CREATE TABLE orders (
  id char(36) NOT NULL,
   order_date VARCHAR(255) NULL,
   user_id char(36) NULL,
   CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE orders_products (
  id BIGINT AUTO_INCREMENT NOT NULL,
   amount INT NULL,
   product_id char(36) NULL,
   order_id char(36) NULL,
   CONSTRAINT pk_orders_products PRIMARY KEY (id)
);

CREATE TABLE position (
  id char(36) NOT NULL,
   position_name VARCHAR(30) NOT NULL,
   CONSTRAINT pk_position PRIMARY KEY (id)
);

CREATE TABLE product (
  id char(36) NOT NULL,
   product_name VARCHAR(50) NOT NULL,
   `description` TEXT NOT NULL,
   image VARCHAR(50) NOT NULL,
   year BIGINT NOT NULL,
   unit_price FLOAT NOT NULL,
   quantity INT NULL,
   category_id char(36) NOT NULL,
   band_id char(36) NOT NULL,
   CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE roles (
  id char(36) NOT NULL,
   role_name VARCHAR(20) NOT NULL,
   CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE tracks (
  id char(36) NOT NULL,
   track_name VARCHAR(30) NOT NULL,
   video_status VARCHAR(4) NOT NULL,
   album_id char(36) NULL,
   CONSTRAINT pk_tracks PRIMARY KEY (id)
);

CREATE TABLE users (
  id char(36) NOT NULL,
   first_name VARCHAR(35) NOT NULL,
   last_name VARCHAR(35) NOT NULL,
   email VARCHAR(255) NULL,
   passwd VARCHAR(255) NOT NULL,
   address VARCHAR(50) NULL,
   city VARCHAR(50) NULL,
   CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE users_roles (
  role_id char(36) NOT NULL,
   user_id char(36) NOT NULL
);

ALTER TABLE roles ADD CONSTRAINT uc_roles_rolename UNIQUE (role_name);

ALTER TABLE users ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE albums ADD CONSTRAINT FK_ALBUMS_ON_BAND FOREIGN KEY (band_id) REFERENCES band (id);

ALTER TABLE band ADD CONSTRAINT FK_BAND_ON_GENRE FOREIGN KEY (genre_id) REFERENCES genre (id);

ALTER TABLE members ADD CONSTRAINT FK_MEMBERS_ON_BAND FOREIGN KEY (band_id) REFERENCES band (id);

ALTER TABLE orders ADD CONSTRAINT FK_ORDERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE orders_products ADD CONSTRAINT FK_ORDERS_PRODUCTS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE orders_products ADD CONSTRAINT FK_ORDERS_PRODUCTS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE product ADD CONSTRAINT FK_PRODUCT_ON_BAND FOREIGN KEY (band_id) REFERENCES band (id);

ALTER TABLE product ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE tracks ADD CONSTRAINT FK_TRACKS_ON_ALBUM FOREIGN KEY (album_id) REFERENCES albums (id);

ALTER TABLE member_position ADD CONSTRAINT fk_mempos_on_members FOREIGN KEY (member_id) REFERENCES members (id);

ALTER TABLE member_position ADD CONSTRAINT fk_mempos_on_position FOREIGN KEY (position_id) REFERENCES position (id);

ALTER TABLE users_roles ADD CONSTRAINT fk_userol_on_user_role FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE users_roles ADD CONSTRAINT fk_userol_on_users FOREIGN KEY (user_id) REFERENCES users (id);