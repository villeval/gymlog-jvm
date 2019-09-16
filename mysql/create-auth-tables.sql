-- create users table
CREATE TABLE gymlog_db.users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
);

-- create authorities table
CREATE TABLE gymlog_db.authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
);

-- create index
CREATE UNIQUE INDEX ix_auth_username
  on authorities (username,authority);

INSERT INTO gymlog_db.users (username, password, enabled)
  values ('user',
  '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',
  1);

INSERT INTO gymlog_db.authorities (username, authority)
  values ('user', 'ROLE_USER');