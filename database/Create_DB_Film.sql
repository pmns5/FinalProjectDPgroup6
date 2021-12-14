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
    id_user  INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20)
);
CREATE TABLE film
(
    id_film INT AUTO_INCREMENT PRIMARY KEY,
    title   VARCHAR(20),
    genre   VARCHAR(20),
    plot    VARCHAR(500),
    trailer VARCHAR(50),
    poster  LONGBLOB
);
CREATE TABLE feedback
(
    id_film INT NOT NULL,
    id_user INT NOT NULL,
    score   FLOAT CHECK (score > 0 and score < 5),
    comment VARCHAR(500),
    PRIMARY KEY (id_film, id_user),
    CONSTRAINT FK_film_feedback FOREIGN KEY (id_film) REFERENCES FILM (id_film) on update cascade on delete cascade,
    CONSTRAINT FK_user_feedback FOREIGN KEY (id_user) REFERENCES USER (id_user) on update cascade on delete cascade
);
CREATE TABLE actor
(
    id_actor INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(20),
    surname  VARCHAR(20)
);
CREATE TABLE cast
(
    id_film  INT NOT NULL,
    id_actor INT NOT NULL,
    PRIMARY KEY (id_film, id_actor),
    CONSTRAINT FK_film_cast FOREIGN KEY (id_film) REFERENCES film (id_film) on update cascade on delete cascade,
    CONSTRAINT FK_actor_feedback FOREIGN KEY (id_actor) REFERENCES actor (id_actor) on update cascade on delete cascade
);