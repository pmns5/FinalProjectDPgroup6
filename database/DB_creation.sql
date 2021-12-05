DROP DATABASE IF EXISTS FILM_DB;
CREATE DATABASE FILM_DB;
USE FILM_DB;

DROP TABLE IF EXISTS VOTO CASCADE;
DROP TABLE IF EXISTS UTENTE CASCADE;
DROP TABLE IF EXISTS FILM CASCADE;
DROP TABLE IF EXISTS Actors CASCADE;
DROP TABLE IF EXISTS FILM_ATTORE CASCADE;

CREATE TABLE UTENTE
(
    username VARCHAR(20) PRIMARY KEY
);

CREATE TABLE FILM
(
    id        INT NOT NULL AUTO_INCREMENT,
    nome      VARCHAR(20),
    genere    VARCHAR(20),
    trama     VARCHAR(500),
    trailer   VARCHAR(50),
    copertina BLOB,
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

CREATE TABLE Actors
(
    id      INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(50),
    surname VARCHAR(50)
);

CREATE TABLE FILM_ATTORE
(
    film   INT NOT NULL,
    attore INT NOT NULL,
    PRIMARY KEY (film, attore),
    CONSTRAINT FK_film_cast FOREIGN KEY (film) REFERENCES FILM (id) on update cascade on delete cascade,
    CONSTRAINT FK_attore_voto FOREIGN KEY (attore) REFERENCES Actors(id) on update cascade on delete cascade
);