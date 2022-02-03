DROP DATABASE IF EXISTS User_Db;
CREATE DATABASE User_Db;
USE User_Db;
DROP TABLE IF EXISTS user CASCADE;
CREATE TABLE user
(
    id_user  INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) UNIQUE,
    email    VARCHAR(50) UNIQUE,
    password VARCHAR(20),
    role     VARCHAR(7) CHECK (role = 'manager' or role = 'client') default 'client',
    ban      INT
);