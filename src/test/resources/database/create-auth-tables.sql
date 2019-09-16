
CREATE TABLE gymlog_db.users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled INTEGER
);

CREATE TABLE gymlog_db.authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL
);

INSERT INTO gymlog_db.users (username, password, enabled)
  values ('user',
  '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',
  1);

INSERT INTO gymlog_db.authorities (username, authority)
  values ('user', 'ROLE_USER');