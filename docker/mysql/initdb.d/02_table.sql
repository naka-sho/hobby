USE cryptocurrency;

DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id   INT(10),
    name VARCHAR(40)
);

INSERT INTO users (id, name)
VALUES (1, "nakao");
