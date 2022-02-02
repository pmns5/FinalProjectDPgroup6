DROP DATABASE IF EXISTS Film_Db;
CREATE DATABASE Film_Db;
USE Film_Db;

DROP TABLE IF EXISTS feedback CASCADE;
DROP TABLE IF EXISTS user CASCADE;
DROP TABLE IF EXISTS film CASCADE;
DROP TABLE IF EXISTS actor CASCADE;
DROP TABLE IF EXISTS cast CASCADE;

CREATE TABLE user
(
    id_user  INT PRIMARY KEY,
    username VARCHAR(1024)
);
CREATE TABLE film
(
    id_film INT AUTO_INCREMENT PRIMARY KEY,
    title   VARCHAR(1024),
    genre   VARCHAR(50),
    plot    VARCHAR(1024),
    trailer VARCHAR(50),
    poster  LONGBLOB
);
CREATE TABLE feedback
(
    id_film INT UNIQUE,
    id_user INT UNIQUE,
    score   FLOAT CHECK (score >= 0 and score <= 5),
    comment VARCHAR(1024),
    date    DATE,
    PRIMARY KEY (id_film, id_user)
);
CREATE TABLE actor
(
    id_actor INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(1024),
    surname  VARCHAR(1024)
);
CREATE TABLE cast
(
    id_film  INT NOT NULL,
    id_actor INT NOT NULL,
    PRIMARY KEY (id_film, id_actor),
    CONSTRAINT FK_film_cast FOREIGN KEY (id_film) REFERENCES film (id_film) on update cascade on delete cascade,
    CONSTRAINT FK_actor_feedback FOREIGN KEY (id_actor) REFERENCES actor (id_actor) on update cascade on delete cascade
);