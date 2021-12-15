USE Film_Db;

INSERT INTO ACTOR (name, surname)
VALUES ('Tom', 'Cruise');
INSERT INTO ACTOR (name, surname)
VALUES ('Angelina', 'Jolie');
INSERT INTO ACTOR (name, surname)
VALUES ('Brad', 'Pitt');
INSERT INTO ACTOR (name, surname)
VALUES ('George', 'Clooney');
INSERT INTO ACTOR (name, surname)
VALUES ('Dwayne', 'Johnson');
INSERT INTO ACTOR (name, surname)
VALUES ('Robert', 'Downey');
INSERT INTO ACTOR (name, surname)
VALUES ('Bradly', 'Cooper');
INSERT INTO ACTOR (name, surname)
VALUES ('Al', 'Pacino');
INSERT INTO ACTOR (name, surname)
VALUES ('Tom', 'Holland');
INSERT INTO ACTOR (name, surname)
VALUES ('Matt', 'Damon');
INSERT INTO ACTOR (name, surname)
VALUES ('Robert', 'De Niro');
INSERT INTO ACTOR (name, surname)
VALUES ('Jennifer', 'Lawrence');
INSERT INTO ACTOR (name, surname)
VALUES ('Jennifer', 'Aniston');
INSERT INTO ACTOR (name, surname)
VALUES ('Robert', 'Williams');
INSERT INTO ACTOR (name, surname)
VALUES ('Tom', 'Hardy');
INSERT INTO ACTOR (name, surname)
VALUES ('Scarlett', 'Johansson');
INSERT INTO ACTOR (name, surname)
VALUES ('Margot', 'Robbie');
INSERT INTO ACTOR (name, surname)
VALUES ('Johnny', 'Deep');
INSERT INTO ACTOR (name, surname)
VALUES ('Samuel', 'Jackson');
INSERT INTO ACTOR (name, surname)
VALUES ('Henry', 'Ford');

INSERT INTO FILM (title, genre, plot, trailer, poster)
VALUES ('Pirati dei Caraibi', 'Avventura', 'Tanti pirati e tante navi', 'www.youtube.com', LOAD_FILE('.\\pirati.jpg'));
INSERT INTO FILM (title, genre, plot, trailer, poster)
VALUES ('Mission Impossible', 'Spionaggio', 'Tante spie e acrobazie', 'www.youtube.com', LOAD_FILE('.\\pirati.jpg'));
INSERT INTO FILM (title, genre, plot, trailer, poster)
VALUES ('Jumanji', 'Avventura', 'Tanta giungla', 'www.youtube.com', LOAD_FILE('.\\pirati.jpg'));
INSERT INTO FILM (title, genre, plot, trailer, poster)
VALUES ('Avengers', 'Marvel', 'Tanti supereroi', 'www.youtube.com', LOAD_FILE('.\\pirati.jpg'));
INSERT INTO FILM (title, genre, plot, trailer, poster)
VALUES ('The departed', 'Storico', 'Tanti soldi e droga', 'www.youtube.com', LOAD_FILE('.\\pirati.jpg'));

INSERT INTO CAST (id_film, id_actor)
VALUES (1, 1);
INSERT INTO CAST (id_film, id_actor)
VALUES (1, 2);
INSERT INTO CAST (id_film, id_actor)
VALUES (1, 3);
INSERT INTO CAST (id_film, id_actor)
VALUES (2, 4);
INSERT INTO CAST (id_film, id_actor)
VALUES (2, 5);
INSERT INTO CAST (id_film, id_actor)
VALUES (2, 6);
INSERT INTO CAST (id_film, id_actor)
VALUES (3, 7);
INSERT INTO CAST (id_film, id_actor)
VALUES (3, 8);
INSERT INTO CAST (id_film, id_actor)
VALUES (3, 9);
INSERT INTO CAST (id_film, id_actor)
VALUES (4, 10);
INSERT INTO CAST (id_film, id_actor)
VALUES (4, 11);
INSERT INTO CAST (id_film, id_actor)
VALUES (4, 12);
INSERT INTO CAST (id_film, id_actor)
VALUES (5, 13);
INSERT INTO CAST (id_film, id_actor)
VALUES (5, 14);
INSERT INTO CAST (id_film, id_actor)
VALUES (5, 15);

INSERT INTO USER (username)
VALUES ('giuseppe');
INSERT INTO USER (username)
VALUES ('paolo');
INSERT INTO USER (username)
VALUES ('vincenzo');
INSERT INTO USER (username)
VALUES ('dario');
INSERT INTO USER (username)
VALUES ('nicola');
INSERT INTO USER (username)
VALUES ('francesco');

INSERT INTO FEEDBACK (id_film, id_user, score, comment)
VALUES (1, 1, 3, 'Film carino');
INSERT INTO FEEDBACK (id_film, id_user, score, comment)
VALUES (2, 1, 1, 'Film brutto');
INSERT INTO FEEDBACK (id_film, id_user, score, comment)
VALUES (2, 2, 4.5, 'Film stupendo');
INSERT INTO FEEDBACK (id_film, id_user, score, comment)
VALUES (3, 5, 1.2, 'Film orrendo');
INSERT INTO FEEDBACK (id_film, id_user, score, comment)
VALUES (5, 3, 4, 'Si può fare di più');