# eGrimpe
Projet 06 OC

### Contexte
Cette application eGrimpe a été developpée par Rémy Bourdoncle, il s'agit du 6eme projet du cursus D&eacute;veloppeur d'application Java propos&eacute; par OpenClassrooms.

### Contenu
Site de partage sur le thème de l'escalade.

### Pré-requis
Version de Java : 1.8
JDK : jdk1.8.0_191
Maven 3.6
### Base de données
PostgresSQL

### Installation et déploiement
Packaging  
`mvn clean package`

Aller dans target et lancer le war avec la commande 
`java -jar egrimpe-0.0.1-SNAPSHOT.war`

Le port de l'Application est paramétré dans application.propertie  : `http://localhost:8090/`
Par défaut mode dev (base mémoire H2), la base est peuplée avec un jeu de données de tests.

L'application est livrée avec 2 configurations 
- **dev** avec une base de données en mémoire (H2) créée à chaque lancement et peuplée avec le contenu du script src\resources\data.sql.
 Les mots de passes sont dans le data.sql
- **prod** avec une base de données PostgreSQL à peupler. Lors du 1er lancement pour la créer il faut mettre à **creat-drop**
 le paramètre spring.jpa.hibernate.ddl-auto, 
ensuite si vous voulez conserver le contenu aux lancements suivants positionnez à **update** la valeur de ce paramètre.

Sources disponibles sur : https://github.com/Rondoublec/eGrimpe
