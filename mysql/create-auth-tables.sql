-- create users
CREATE TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(45) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));

-- create roles
CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));

-- create users
INSERT INTO users(username,password,enabled)
VALUES ('john','password1', true);
INSERT INTO users(username,password,enabled)
VALUES ('julia','password2', true);

-- assign roles
INSERT INTO user_roles (username, role)
VALUES ('john', 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ('john', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role)
VALUES ('julia', 'ROLE_USER');