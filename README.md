
# eGrimpe
Site de partage et d'&eacute;change d'informations sur les "spots" d'escalade, et de mise en relation pour les prêts de "topos" (document d&eacute;taillant les informations techniques d'un spot d'escalade).
  
### Contexte  
Cette application eGrimpe a &eacute;t&eacute; developp&eacute;e par R&eacute;my Bourdoncle, il s'agit du 6eme projet du cursus D&eacute;veloppeur d'application Java propos&eacute; par OpenClassrooms.  
  
### Pré-requis technique  
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
Par défaut mode **dev** (base mémoire H2), la base est peuplée avec un jeu de données de tests.  
  
L'application est livrée avec 2 configurations   
- **dev** avec une base de données en mémoire (H2) créée à chaque lancement et peuplée avec le contenu du script src\resources\data.sql.  
 Les mots de passes sont dans le data.sql  
- **prod** avec une base de données PostgreSQL à peupler. Lors du 1er lancement pour créer le modèle il faut mettre à **creat-drop** le paramètre spring.jpa.hibernate.ddl-auto dans le fichier application.properties correspondant.
Pour conserver le contenu aux lancements suivants positionnez à **update** la valeur de ce paramètre.
*Vous devez tout de même au préalable avoir créé la base de données avec son compte de propriétaire -> ces informations seront à mettre à jour dans le fichier application.properties correspondant à la configuration de l'application.*

**Logs :** Par défaut le niveau de log est positionné à "DEBUG", les logs sont quotidiens (hordatés) et se trouvent dans le répèrtoire **logs**, tous ces paramétrages sont dans le fichier **src\main\resources\logback.xml**

**Documentation :** la javadoc peut être consultée en lançant le fichier **docs\index.html**
  
**Remarque :** *Si vous commencez avec une base vierge, vous pouvez créer des comptes avec privilèges MEMBRE et ADMIN en lançant l'application avec les paramètres :  
CREATE_CPT (création d'un compte MEMBRE m0@a.a et un compte ADMIN a0@a.a MdP 12345)  
CREATE_MEMBRE (création d'un compte MEMBRE m0@a.a seulement)  
CREATE_ADMIN (création d'un compte ADMIN a0@a.a seulement) *  
`java -jar egrimpe-0.0.1-SNAPSHOT.war CREATE_MEMBRE CREATE_ADMIN`  
  
Sources disponibles sur : https://github.com/Rondoublec/eGrimpe
