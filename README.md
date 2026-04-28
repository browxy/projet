# Image Manager

Application de gestion et de traitement d'images en JavaFX.

## Fonctionnalités

*   **Gestion de Fichiers** : Chargement d'images, sauvegarde dans une bibliothèque locale, et rechargement.
*   **Filtres de Couleur** : 
    *   Noir et Blanc
    *   Sépia
    *   Échange RGB
    *   Contours (Filtre de Prewitt)
*   **Transformations Géométriques** :
    *   Rotations 
    *   Symétries (horizontale et verticale)
*   **Sécurité** : Chiffrement et déchiffrement des images sauvegardées par mot de passe.
*   **Tags & Bibliothèque** : Ajout de tags aux images, sauvegarde en bibliothèque et recherche rapide par tag.
*   **Interface Moderne** : Thème sombre (Dark mode) intégré et interface divisée en panneaux pour une meilleure ergonomie.

## Architecture (MVC)

Le projet suit le patron de conception Modèle-Vue-Contrôleur (MVC) pour garantir un code propre et maintenable :
*   **Modèle (`src/main/java/model/`)** : Contient les données (ImageModel, ImageData) et la logique des filtres.
*   **Vue (`src/main/resources/`)** : Fichiers FXML fractionnés (`main.fxml`, `left_panel.fxml`, etc.) et feuille de style `style.css`.
*   **Contrôleur (`src/main/java/controller/`)** : Gère les interactions utilisateur, avec un contrôleur principal et des sous-contrôleurs par panneau.
*   **Services (`src/main/java/service/`)** : Isoler la logique métier (ImageService, PersistenceService, TagService).

## Prérequis

*   Java JDK 21 (ou supérieur)
*   Maven 3.x

## Compilation et Exécution

Ouvrez un terminal à la racine du projet (là où se trouve le `pom.xml`) et exécutez la commande suivante :

```bash
mvn clean compile javafx:run
```
