 CREATE TABLE users(
 	id BIGINT,
 	username TEXT NOT NULL,
 	password TEXT NOT NULL,
 	bio TEXT,
 	profilepicurl TEXT
 );
 ALTER TABLE users ADD CONSTRAINT users_pKey PRIMARY KEY (id);
 ALTER TABLE users ADD CONSTRAINT users_uKey UNIQUE (username);
 
 CREATE TABLE feed(
 	id BIGINT,
 	users_id BIGINT NOT NULL,
 	posturl TEXT NOT NULL,
 	description TEXT,
 	privacy BOOLEAN DEFAULT FALSE
 );
 ALTER TABLE feed ADD CONSTRAINT feed_pKey PRIMARY KEY (id);
 
 ALTER TABLE feed ADD CONSTRAINT feed_fKey FOREIGN KEY (users_id) REFERENCES users(id);