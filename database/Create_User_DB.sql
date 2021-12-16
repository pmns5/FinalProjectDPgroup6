DROP DATABASE IF EXISTS User_Db;
CREATE DATABASE User_Db;
USE User_Db;

DROP TABLE IF EXISTS user CASCADE;

CREATE TABLE user
(
    id_user       INT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(20) UNIQUE,
    email         VARCHAR(20) UNIQUE,
    user_password VARCHAR(20),
    ban           INT
);