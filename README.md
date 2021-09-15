# followMyMeals

Durant le parcours Développeur d’application - Java dispensé par Openclassrooms, un projet personnel doit être réalisé. 

followMyMeals est une application de suivi de repas. Les utilisateurs peuvent ainsi comprendre ce qu’ils mangent simplement et sans dogmatisme. 
  
Afin respecter les exigences d’Openclassrooms concernant les objectifs pédagogiques, le projet est présenté sous licence opensource. 

Le projet est composé de 6 micro-services :

- Le microservice Spring Coud Config rassemble l’ensemble des informations de configuration de l’application. 
- Le microservice Eureka assure l’enregistrement des micro-services de l’application 
- Le microservice Gateway Zuul assure le routage et la sécurité de chaque requête du front vers chaque microservice. Il assure également l’équilibrage de la charge. 
- Le microservice KeyCloak assure la création et la gestion des comptes des utilisateurs. Il délivre également les tokens de connexion.
- Le microservice Stock permet aux utilisateurs de consulter et d’enregistrer les informations nutritionnelles des produits vendu dans les magasins alimentaires. Il contient également les fonctionnalités pour enregistrer les produits dans un stock.
- Le microservice Meals permet aux utilisateurs de réaliser et/ou modifier des repas et de consulter un suivi nutritionnel.

## Prérequis
Java 11
MySql 8.0.20
Maven 3.3.9

## Déploiment

### Clonez les projets :
```
git clone https://github.com/nicolasdotnet/followMyMeals.git
```
```
git clone https://github.com/nicolasdotnet/followMyMeals-conf.git
```

### Déployer Back-office

- Placer à la racine de votre disque dur le projet followMyMeals-conf

- user-service : dans votre terminal à la racine du dossier user_service/bin, exécutez la commande : 
```
bash standalone.sh
```
- config-service / register-service / proxy-service : exécutez à la racine de chaque projet, la commande maven : 
```
mvn spring-boot:run
```

- stock-service : créer une base de données dans MySql avec les paramétres suivants :

```
      Nom de la base : db_stock
      Identifiant : root
      Mot de passe : mysql
```
A la racine du projet, exécutez la commande ```mvn spring-boot:run```.

- meals-service : créer une base de données dans MySql avec les paramétres suivants :

```
    Nom de la base : db_meals
    Identifiant : root
    Mot de passe : mysql
```
A la racine du projet, exécutez la commande ```mvn spring-boot:run```.

Pour une installation personnalisée, vous pouvez modifier les valeurs des clès des fichiers de configuration ".properties" du projet FollowMyMealsConf :
```
spring.datasource.url=jdbc:mysql://localhost:3306/NOM_DATABASE?serverTimezone=Europe/Paris
spring.datasource.username=VOTRE_USERNAME
spring.datasource.password=VOTRE_PASSWORD
```

### Déployer Front Office

A la racine du projet Front-service, exécutez la commande ```mvn spring-boot:run```.

### Demo

Il existe un compte utilisateur dans la base de demo :

- Identifiant : nicolas@fakemail.com Mot de passe : 123
