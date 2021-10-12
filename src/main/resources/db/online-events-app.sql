DROP TABLE IF EXISTS online_events.korisnik_dogadaj;
DROP TABLE IF EXISTS online_events.dogadaj;
DROP TABLE IF EXISTS online_events.grad;
DROP TABLE IF EXISTS online_events.velicina_grada;
DROP TABLE IF EXISTS online_events.organizacijska_jedinica;
DROP TABLE IF EXISTS online_events.tip_organizacijske_jedinice;
DROP SCHEMA IF EXISTS online_events;


CREATE SCHEMA online_events;

CREATE TABLE online_events.korisnik
(
    sifra BIGINT PRIMARY KEY  AUTO_INCREMENT,
    korisnicko_ime VARCHAR(50) NOT NULL,
    ime VARCHAR(50) NOT NULL,
    prezime VARCHAR(50) NOT NULL,
    oib VARCHAR(11) NOT NULL,
    email VARCHAR(11) NOT NULL
);


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


CREATE TABLE online_events.dogadaj
(
    sifra BIGINT PRIMARY KEY  AUTO_INCREMENT,
    naziv VARCHAR(250) NOT NULL,
    vrijeme_od TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    vrijeme_do TIMESTAMP NULL,
    slobodan_ulaz VARCHAR(2) NOT NULL,
    grad BIGINT NOT NULL,
    FOREIGN KEY (grad) REFERENCES online_events.grad (sifra)
);

CREATE TABLE online_events.korisnik_dogadaj
(
    sifra        BIGINT PRIMARY KEY  AUTO_INCREMENT,
    korisnik     BIGINT     NOT NULL,
    dogadaj      BIGINT     NOT NULL,
    FOREIGN KEY (korisnik) REFERENCES online_events.korisnik (sifra),
    FOREIGN KEY (dogadaj) REFERENCES online_events.dogadaj (sifra)
);

DROP TABLE online_events.korisnik_dogadaj;
DROP TABLE online_events.korisnik;

CREATE TABLE online_events.korisnik
(
    korisnicko_ime VARCHAR(20) PRIMARY KEY ,
    ime VARCHAR(50) NOT NULL,
    prezime VARCHAR(50) NOT NULL,
    oib VARCHAR(11) NOT NULL,
    email VARCHAR(11) NOT NULL,
    tip_korisnika VARCHAR(50)    NOT NULL
);

ALTER TABLE online_events.dogadaj
    ADD kreator VARCHAR(20);

ALTER TABLE online_events.dogadaj
    ADD FOREIGN KEY (kreator) REFERENCES online_events.korisnik(korisnicko_ime);

ALTER TABLE online_events.korisnik MODIFY email VARCHAR(50) ;

ALTER TABLE online_events.korisnik DROP COLUMN ime;
ALTER TABLE online_events.korisnik DROP COLUMN prezime;
ALTER TABLE online_events.korisnik DROP COLUMN oib;
ALTER TABLE online_events.korisnik DROP COLUMN email;
ALTER TABLE online_events.korisnik DROP COLUMN tip_korisnika;

CREATE TABLE online_events.korisnik_dogadaj
(
    korisnik     VARCHAR(20)     NOT NULL,
    dogadaj      BIGINT     NOT NULL,
    PRIMARY KEY (korisnik, dogadaj),
    FOREIGN KEY (korisnik) REFERENCES online_events.korisnik (korisnicko_ime),
    FOREIGN KEY (dogadaj) REFERENCES online_events.dogadaj (sifra)
);
# INSERT INTO online_events.korisnik(korisnicko_ime, ime, prezime, oib, email, tip_korisnika) VALUES ('rudek', 'Rudolf', 'Proskura', '00000000000', 'rudolf@gmail.com','admin');
# INSERT INTO online_events.korisnik(korisnicko_ime, ime, prezime, oib, email, tip_korisnika) VALUES ('marina', 'Marina', 'Kovač', '12121212121', 'marina@gmail.com','admin');
#
# INSERT INTO online_events.korisnik(korisnicko_ime, ime, prezime, oib, email, tip_korisnika) VALUES ('bartol', 'Bartol', 'Proskura', '11111111111', 'bartol@gmail.com','organizer');
# INSERT INTO online_events.korisnik(korisnicko_ime, ime, prezime, oib, email, tip_korisnika) VALUES ('helena', 'Helena', 'Boban', '22222222222', 'helena@gmail.com','organizer');
# INSERT INTO online_events.korisnik(korisnicko_ime, ime, prezime, oib, email, tip_korisnika) VALUES ('iva', 'Iva', 'Šuker', '33333333333', 'iva@gmail.com','organizer');
# INSERT INTO online_events.korisnik(korisnicko_ime, ime, prezime, oib, email, tip_korisnika) VALUES ('danijel', 'Danijel', 'Davidenko', '44444444444', 'danijel@gmail.com','organizer');

# INSERT INTO online_events.korisnik(korisnicko_ime, ime, prezime, oib, email, tip_korisnika) VALUES ('tihana', 'Tihana', 'Medved', '55555555555', 'tihana@gmail.com','user');
# INSERT INTO online_events.korisnik(korisnicko_ime, ime, prezime, oib, email, tip_korisnika) VALUES ('igor', 'Igor', 'Rakitić', '66666666666', 'igor@gmail.com','user');
# INSERT INTO online_events.korisnik(korisnicko_ime, ime, prezime, oib, email, tip_korisnika) VALUES ('anita', 'Anita', 'Lovren', '77777777777', 'anita@gmail.com','user');
# # INSERT INTO online_events.korisnik(korisnicko_ime, ime, prezime, oib, email, tip_korisnika) VALUES ('boris', 'Boris', 'Srna', '88888888888', 'boris@gmail.com','user');
# # INSERT INTO online_events.korisnik(korisnicko_ime, ime, prezime, oib, email, tip_korisnika) VALUES ('ena', 'Ena', 'Rebić', '99999999999', 'ena@gmail.com','user');
# --INSERT INTO online_events.korisnik(korisnicko_ime, ime, prezime, oib, email, tip_korisnika) VALUES ('tea', 'Tea', 'Majer', '10101010101', 'tea@gmail.com','user');

INSERT INTO online_events.tip_organizacijske_jedinice(sifra, naziv, aktivan) VALUES (1, 'REGIJA', true);
INSERT INTO online_events.tip_organizacijske_jedinice(sifra, naziv, aktivan) VALUES (2, 'ŽUPANIJA', true);

INSERT INTO online_events.velicina_grada(sifra, naziv, aktivan) VALUES (1, 'MALI', true);
INSERT INTO online_events.velicina_grada(sifra, naziv, aktivan) VALUES (2, 'SREDNJI', true);
INSERT INTO online_events.velicina_grada(sifra, naziv, aktivan) VALUES (3, 'VELIKI', true);

INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (1, 'Sjeverozapadna Hrvatska', 'Eurostat regija', 1, null);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (2, 'Središnja i Istočna (Panonska) Hrvatska', 'Eurostat regija', 1, null);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (3, 'Jadranska Hrvatska', 'Eurostat regija', 1, null);

INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (4, 'Grad Zagreb', null, 2, 1);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (5, 'Zagrebačka županija', null, 2, 1);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (6, 'Krapinsko-zagorska županija', null, 2, 1);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (7, 'Varaždinska županija', null, 2, 1);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (8, 'Koprivničko-križevačka županija', null, 2, 1);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (9, 'Međimurska županija', null, 2, 1);

INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (10, 'Bjelovarsko-bilogorska županija', null, 2, 2);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (11, 'Virovitičko-podravska županija', null, 2, 2);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (12, 'Požeško-slavonska županija', null, 2, 2);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (13, 'Brodsko-posavska županija', null, 2, 2);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (14, 'Osječko-baranjska županija', null, 2, 2);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (15, 'Vukovarsko-srijemska županija', null, 2, 2);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (16, 'Karlovačka županija', null, 2, 2);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (17, 'Sisačko-moslavačka županija', null, 2, 2);

INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (18, 'Primorsko-goranska županija', null, 2, 3);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (19, 'Ličko-senjska županija', null, 2, 3);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (20, 'Zadarska županija', null, 2, 3);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (21, 'Šibensko-kninska županija', null, 2, 3);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (22, 'Splitsko-dalmatinska županija', null, 2, 3);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (23, 'Istarska županija', null, 2, 3);
INSERT INTO online_events.organizacijska_jedinica(sifra, naziv, opis, tip_org_jedinice, org_jedinica) VALUES (24, 'Dubrovačko-neretvanska županija', null, 2, 3);


INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (127, 'Dugo Selo', 1, 5);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (1, 'Ivanić Grad', 1, 5);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (2, 'Jastrebarsko', 1, 5);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (3, 'Samobor', 1, 5);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (4, 'Sveta Nedelja', 1, 5);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (5, 'Sveti Ivan Zelina', 1, 5);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (6, 'Velika Gorica', 2, 5);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (7, 'Vrbovec', 1, 5);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (8, 'Zagreb', 3, 5);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (9, 'Zaprešić', 2, 5);



INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (10, 'Donja Stubica', 1, 6);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (11, 'Klanjec', 1, 6);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (12, 'Krapina', 2, 6);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (13, 'Oroslavje', 1, 6);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (14, 'Pregrada', 1, 6);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (15, 'Zabok', 1, 6);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (16, 'Zlatar', 1, 6);


INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (17, 'Ivanec', 1, 7);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (18, 'Lepoglava', 1, 7);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (19, 'Ludbreg', 1, 7);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (20, 'Novi Marof', 1, 7);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (21, 'Varaždin', 3, 7);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (22, 'Varaždinske Toplice', 1, 7);



INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (23, 'Đurđevac', 1, 8);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (24, 'Koprivnica', 2, 8);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (25, 'Križevci', 1, 8);


INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (26, 'Čakovec', 2, 9);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (27, 'Mursko Središće', 1, 9);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (28, 'Prelog', 1, 9);


INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (29, 'Bjelovar', 2, 10);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (30, 'Čazma', 1, 10);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (31, 'Daruvar', 1, 10);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (32, 'Garešnica', 1, 10);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (33, 'Grubišno Polje', 1, 10);


INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (34, 'Orahovica', 1, 11);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (35, 'Slatina', 1, 11);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (36, 'Virovitica ', 1, 11);


INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (37, 'Kutjevo', 1, 12);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (38, 'Lipik', 1, 12);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (39, 'Pakrac ', 1, 12);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (40, 'Pleternica ', 1, 12);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (41, 'Požega ', 1, 12);


INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (42, 'Nova Gradiška', 1, 13);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (43, 'Slavonski Brod', 2, 13);


INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (44, 'Beli Manastir', 1, 14);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (45, 'Belišće', 1, 14);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (46, 'Donji Miholjac', 1, 14);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (47, 'Đakovo', 1, 14);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (48, 'Našice', 1, 14);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (49, 'Osijek', 3, 14);


INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (50, 'Ilok', 1, 15);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (51, 'Otok', 1, 15);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (52, 'Vinkovci', 1, 15);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (53, 'Vukovar', 2, 15);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (54, 'Županja', 1, 15);


INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (55, 'Duga Resa', 1, 16);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (56, 'Karlovac', 3, 16);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (57, 'Ogulin', 1, 16);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (58, 'Ozalj', 1, 16);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (59, 'Slunj', 1, 16);

INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (60, 'Glina', 1, 17);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (61, 'Hrvatska Kostajnica', 1, 17);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (62, 'Kutina', 1, 17);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (63, 'Novska', 1, 17);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (64, 'Petrinja', 1, 17);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (65, 'Popovača', 1, 17);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (66, 'Sisak ', 2, 17);

INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (67, 'Bakar', 1, 18);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (68, 'Cres', 1, 18);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (69, 'Crikvenica', 1, 18);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (70, 'Čabar', 1, 18);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (71, 'Delnice', 1, 18);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (72, 'Kastav', 1, 18);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (73, 'Kraljevica', 2, 18);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (74, 'Krk', 1, 18);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (75, 'Mali Lošinj', 1, 18);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (76, 'Novi Vinodolski', 1, 18);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (77, 'Opatija', 1, 18);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (78, 'Rab', 1, 18);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (79, 'Rijeka', 3, 18);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (80, 'Vrbovsko ', 1, 18);

INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (81, 'Gospić ', 1, 19);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (82, 'Novalja', 1, 19);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (83, 'Otočac', 1, 19);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (84, 'Senj', 1, 19);


INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (85, 'Benkovac ', 1, 20);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (86, 'Biograd na Moru', 1, 20);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (87, 'Nin', 1, 20);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (88, 'Obrovac', 1, 20);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (89, 'Pag', 1, 20);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (90, 'Zadar', 3, 20);

INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (91, 'Drniš ', 1, 21);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (92, 'Knin', 1, 21);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (93, 'Skradin', 1, 21);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (94, 'Šibenik', 3, 21);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (95, 'Vodice', 1, 21);


INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (96, 'Hvar ', 1, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (97, 'Imotski', 1, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (98, 'Kaštela', 2, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (99, 'Komiža', 1, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (100, 'Makarska', 1, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (101, 'Omiš', 1, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (102, 'Sinj', 1, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (103, 'Solin', 1, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (104, 'Split ', 3, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (105, 'Stari Grad', 1, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (106, 'Supetar', 1, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (107, 'Trilj', 1, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (108, 'Trogir ', 1, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (109, 'Vis', 1, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (110, 'Vrgorac', 1, 22);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (111, 'Vrlika', 3, 22);


INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (112, 'Buje', 1, 23);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (113, 'Buzet', 1, 23);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (114, 'Labin ', 1, 23);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (115, 'Novigrad', 1, 23);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (116, 'Pazin', 1, 23);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (117, 'Poreč', 1, 23);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (118, 'Pula ', 3, 23);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (119, 'Rovinj', 1, 23);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (120, 'Umag', 1, 23);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (121, 'Vodnjan', 1, 23);

INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (122, 'Dubrovnik', 3, 24);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (123, 'Korčula ', 3, 24);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (124, 'Metković', 1, 24);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (125, 'Opuzen', 1, 24);
INSERT INTO online_events.grad(sifra, naziv, velicina, org_jedinica) VALUES (126, 'Ploče', 1, 24);