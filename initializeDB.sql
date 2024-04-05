CREATE DATABASE MASI;

USE MASI;

CREATE TABLE utilisateur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    utilisateurId INT,
    notSoSecretData varchar(255),
    FOREIGN KEY (utilisateurId) REFERENCES utilisateur(id)
);

insert into utilisateur values(null, "user", "password");
insert into utilisateur values(null, "sami", "password");

insert into value values(null, 2, "stuff1");
insert into value values(null, 2, "stuff2");
insert into value values(null, 2, "stuff3");