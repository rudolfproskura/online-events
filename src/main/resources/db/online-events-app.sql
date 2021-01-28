DROP TABLE IF EXISTS online_events.dogadaj;
DROP TABLE IF EXISTS online_events.grad;
DROP TABLE IF EXISTS online_events.velicina_grada;
DROP TABLE IF EXISTS online_events.organizacijska_jedinica;
DROP TABLE IF EXISTS online_events.tip_organizacijske_jedinice;
DROP SCHEMA IF EXISTS online_events;


CREATE SCHEMA online_events;

CREATE TABLE online_events.tip_organizacijske_jedinice
(
    sifra BIGINT PRIMARY KEY  AUTO_INCREMENT,
    naziv   VARCHAR(50) NOT NULL,
    aktivan BOOLEAN     NOT NULL
);

CREATE TABLE online_events.velicina_grada
(
    sifra   BIGINT PRIMARY KEY  AUTO_INCREMENT,
    naziv   VARCHAR(50) NOT NULL,
    aktivan BOOLEAN     NOT NULL

);

CREATE TABLE online_events.organizacijska_jedinica
(
    sifra            BIGINT PRIMARY KEY  AUTO_INCREMENT,
    naziv            VARCHAR(50) NOT NULL,
    opis             VARCHAR(200),
    tip_org_jedinice BIGINT NOT NULL,
    org_jedinica     BIGINT,
    FOREIGN KEY (tip_org_jedinice) REFERENCES online_events.tip_organizacijske_jedinice(sifra),
    FOREIGN KEY (org_jedinica) REFERENCES online_events.organizacijska_jedinica(sifra)

);

CREATE TABLE online_events.grad
(
    sifra        BIGINT PRIMARY KEY  AUTO_INCREMENT,
    naziv        VARCHAR(50) NOT NULL,
    velicina     BIGINT     NOT NULL,
    org_jedinica BIGINT     NOT NULL,
    FOREIGN KEY (velicina) REFERENCES online_events.velicina_grada (sifra),
    FOREIGN KEY (org_jedinica) REFERENCES online_events.organizacijska_jedinica (sifra)
);