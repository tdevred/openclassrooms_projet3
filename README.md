# Base de données

Afin de lancer la base de données, j'utilise docker-compose.

Ainsi, pour lancer MySQL, il suffit d'utiliser la commande `docker-compose up` (selon votre installation, avec ou sans besoin d'utiliser les privilèges d'administrateur).

## Initialisation

Pour l'initialisation, la commande `mysql --protocol tcp -h localhost -P 3306 -u user -p rentals < script.sql` vous permet d'éxécuter le script SQL fourni dans le projet pour initiliser les tables de la base.
