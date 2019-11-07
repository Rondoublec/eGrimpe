INSERT INTO utilisateur (utilisateur_id, name, last_name, email, password,active) VALUES (100, 'Alexis', 'Dumay', 'alexis.dumay@axa.fr', '$2a$10$RyY4bXtV3LKkDCutlUTYDOKd2AiJYZGp4Y7MPVdLzWzT1RX.JRZyG',true);
INSERT INTO utilisateur (utilisateur_id,active,email,last_name,name,password) VALUES (300,true,'a@a.a','Rémy','Moi','$2a$10$k9X9iQxEXD4r0ZheiREkCeI9kItSLgdA8.STR8YnpPhfb2YQQq0Fi');
INSERT INTO utilisateur (utilisateur_id,active,email,last_name,name,password) VALUES (400,true,'u@a.a','01','user','$2a$10$0hpDnMgzpDbmH7031ozeFeGaeQgbV2a/IH1a2CZ0y78xUcq9qPxue');
INSERT INTO utilisateur (utilisateur_id,active,email,last_name,name,password) VALUES (500,true,'m@a.a','01','membre','$2a$10$miawCUKytS4UT0UQIaNC8uL5uWh62T9NJOgd8slriE7uY3.F1miRa');

INSERT INTO role (role_id, role) VALUES (10, 'ROLE_ADMIN');
INSERT INTO role (role_id, role) VALUES (20, 'ROLE_UTILISATEUR');
INSERT INTO role (role_id, role) VALUES (30, 'ROLE_AMI_ESCALADE');

INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (300, 10);
INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (400, 20);
INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (500, 30);

INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (100, 10);
INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (100, 20);
INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (100, 30);

INSERT INTO utilisateur (utilisateur_id, name, last_name, email, password,active) VALUES (200, 'Tom', 'Dumoulin', 'alexlanoisette@gmail.fr', '$2a$10$RyY4bXtV3LKkDCutlUTYDOKd2AiJYZGp4Y7MPVdLzWzT1RX.JRZyG',true );
INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (200, 10);
INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (200, 20);

insert into site (id,nom,description,cotation_min,cotation_max,type_roche,ancrage,relais,altitude,orientation,lieu,officiel_ami_escalade) values ('100','Moncel','coteaux','4A','9H','caillou','poteaux','type COLLINOX scéllés',450,'est','54280 Moncel Sur Seille',true);
insert into site (id,nom,description,cotation_min,cotation_max,type_roche,ancrage,relais,altitude,orientation,lieu,officiel_ami_escalade) values ('101','Monce','falaise nord','3','3','pierre','poteaux','type COLLINOX scéllés',450,'nord','Laxou',true);

insert into commentaire (id,message,utilisateur_commentaire_utilisateur_id,site_id) values ('100','Site génial pour grimper','100','101');
insert into commentaire (id,message,utilisateur_commentaire_utilisateur_id,site_id) values ('101','Je me suis cassé la jambe ici, aie aie aie','100','101');

insert into secteur (id,site_id,nom,description) values ('100','100','gare','à droite');
insert into secteur (id,site_id,nom,description) values ('200','100','vaimbois','au milieu');
insert into secteur (id,site_id,nom,description) values ('300','100','lotissement','à gauche');

insert into voie (id,secteur_id,nom,cotation) values ('100','100','soleil','4A');
insert into voie (id,secteur_id,nom,cotation) values ('200','100','lune','9A');

insert into voie (id,secteur_id,nom,cotation) values ('300','200','eclipse','9H');

insert into voie (id,secteur_id,nom,cotation) values ('400','300','sunset','4A');

INSERT into longueur (id,voie_id,cotation,description) values ('100','100','5A','petite longueur');
INSERT into longueur (id,voie_id,cotation,description) values ('200','100','8A','grosse longueur');
INSERT into longueur (id,voie_id,cotation) values ('300','100','5');

INSERT into longueur (id,voie_id,cotation,description) values ('400','200','8A','longueur 1');
INSERT into longueur (id,voie_id,cotation) values ('500','200','5');

INSERT into longueur (id,voie_id,cotation,description) values ('600','300','6A','longueur 1');
INSERT into longueur (id,voie_id,cotation) values ('700','300','7');

INSERT into topo (dispo_pret,lieu, nom,description,utilisateur_proprietaire_utilisateur_id) values (true,'Le bois joli','grimpette','topo dispo','500');
INSERT into topo (dispo_pret,lieu, nom,description,utilisateur_proprietaire_utilisateur_id) values (false ,'La montagne grise','grisaille','topo non dispo','500');
INSERT into topo (dispo_pret,utilisateur_emprunteur_utilisateur_id,lieu, nom,description,utilisateur_proprietaire_utilisateur_id) values (true,'200','joli falaise','grimpette','demande de resa en cours','500');
INSERT into topo (dispo_pret,utilisateur_emprunteur_utilisateur_id,lieu, nom,description,utilisateur_proprietaire_utilisateur_id) values (false,'200','La montagne brune','grisaille','demande acceptée','500');
