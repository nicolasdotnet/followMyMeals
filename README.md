# followMyMeals

AJOUTER DEF DU PROJET

Prérequis
Java 11
MySql 8.0.20
Maven 3.3.9

Déploiment

Clonez le projet :
git clone https://github.com/nicolasdotnet/FollowMyMeals.git

git clone https://github.com/nicolasdotnet/FollowMyMealsConfig.git

le projet est composé de 5 micro-services métier :

•	Le micro-service config-service rassemble l’ensemble des informations de configuration de l’application.

•	Le micro-service register-service assure l’enregistrement, des micro-services de l’application en vue de généré leurs instances à fin d’assurer la monté en charge.

•	Le micro-service proxy-service assure le routage et la sécurité de chaque requête du front vers les instance de chaque micro-service.

•	Le micro-service user-service assure la création et la gestion des comptes des utilisateurs. Il délivre également les tokens de connexion.

•	Le micro-service stock-service permet aux utilisateurs de consulter et d’enregistrer les informations nutritionnelles des produits vendu dans les magasins alimentaires. Il contient également les fonctionnalités pour enregistrer les produits dans un  stock.

•	Le micro-service meals-service permet aux utilisateurs de réaliser et/ou modifier des repas et de consulter un suivie nutritionnel.

Déployer Back-office

- Exécutez la commande maven dans votre terminal à la racine du projet config-service :
mvn spring-boot:run

- Exécutez la commande maven dans votre terminal à la racine du projet register-service :
mvn spring-boot:run

- Exécutez la commande maven dans votre terminal à la racine du projet proxy-service :
mvn spring-boot:run

- Exécutez la commande maven dans votre terminal à la racine du projet user-service :
bash standalone.sh

- stock-service :
Créer une base de donnée dans MySql avec les paramétres suivants :
Nom de la base : db_stock

Identifiant : root
Mot de passe : mysql

Exécutez la commande mvn spring-boot:run à la racine du projet  :

- meals-service :
Créer une base de donnée dans MySql avec les paramétres suivants :
Nom de la base : db_meals

Identifiant : root
Mot de passe : mysql

Exécutez la commande mvn spring-boot:run à la racine du projet  :

- Pour une installation personnalisée, vous pouvez modifier les valeurs des clès des fichiers de configuration ".properties" du projet FollowMyMealsConfig :

spring.datasource.url=jdbc:mysql://localhost:3306/NOM_DATABASE?serverTimezone=Europe/Paris
spring.datasource.username=VOTRE_USERNAME
spring.datasource.password=VOTRE_PASSWORD


Déployer Front Office

- Exécutez la commande maven dans votre terminal à la racine du projet Front-service :
mvn spring-boot:run

Il existe un compte usager dans la base démo :

### usager :

Identifiant : utilisateur@mail.com Mot de passe : 123

Consulter la documentation des micro-services stock et meals:
Aller sur un navigateur à l'adresse http://localhost:2222/swagger-ui.html

Aller sur un navigateur à l'adresse http://localhost:2222/swagger-ui.html
 
