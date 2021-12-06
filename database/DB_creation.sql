DROP DATABASE IF EXISTS FILM_DB;
CREATE DATABASE FILM_DB;
USE FILM_DB;

DROP TABLE IF EXISTS user CASCADE;
CREATE TABLE user
(
    username VARCHAR(20) PRIMARY KEY
);

DROP TABLE IF EXISTS film CASCADE;
CREATE TABLE film
(
    id        INT NOT NULL AUTO_INCREMENT,
    title      VARCHAR(20),
    genre    VARCHAR(20),
    plot     VARCHAR(500),
    trailer   VARCHAR(50),
    poster LONGBLOB,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS vote CASCADE;
CREATE TABLE vote
(
    film     INT         NOT NULL,
    username VARCHAR(20) NOT NULL,
    voto     FLOAT CHECK (voto > 0 and voto < 5),
    commento VARCHAR(500),
    PRIMARY KEY (film, username),
    CONSTRAINT fk_film_vote FOREIGN KEY (film) REFERENCES FILM (id) on update cascade on delete cascade,
    CONSTRAINT fk_user_vote FOREIGN KEY (username) REFERENCES UTENTE (username) on update cascade on delete cascade
);


DROP TABLE IF EXISTS actor CASCADE;
CREATE TABLE actor
(
    id      INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(50),
    surname VARCHAR(50)
);

DROP TABLE IF EXISTS cast CASCADE;
CREATE TABLE cast
(
    id_film   INT NOT NULL,
    id_actor INT NOT NULL,
    PRIMARY KEY (id_film, id_actor),
    CONSTRAINT FK_film_cast FOREIGN KEY (id_film) REFERENCES film (id) on update cascade on delete cascade,
    CONSTRAINT FK_attore_voto FOREIGN KEY (id_actor) REFERENCES actors(id) on update cascade on delete cascade
);