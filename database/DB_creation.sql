DROP DATABASE IF EXISTS FILM_DB;
CREATE DATABASE FILM_DB;
USE FILM_DB;

DROP TABLE IF EXISTS VOTO CASCADE;


DROP TABLE IF EXISTS UTENTE CASCADE;
CREATE TABLE UTENTE
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

CREATE TABLE VOTO
(
    film     INT         NOT NULL,
    username VARCHAR(20) NOT NULL,
    voto     FLOAT CHECK (voto > 0 and voto < 5),
    commento VARCHAR(500),
    PRIMARY KEY (film, username),
    CONSTRAINT FK_film_voto FOREIGN KEY (film) REFERENCES FILM (id) on update cascade on delete cascade,
    CONSTRAINT FK_utente_voto FOREIGN KEY (username) REFERENCES UTENTE (username) on update cascade on delete cascade
);


DROP TABLE IF EXISTS Actors CASCADE;
CREATE TABLE Actors
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