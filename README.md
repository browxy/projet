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
*   **Interface Moderne** : Interface divisée en panneaux (Fichiers/Filtres, Image, Bibliothèque) pour une meilleure ergonomie.

## Architecture (MVC)

Le projet suit le patron de conception Modèle-Vue-Contrôleur (MVC) pour garantir un code propre et maintenable. Le code est organisé sous le package racine `com.imagemanager` :

*   **Modèle (`src/main/java/com/imagemanager/model/`)** : Contient les données (ImageModel, ImageData, ImageLibrary) et les classes implémentant l'application des filtres (`filters/`).
*   **Vue (`src/main/resources/com/imagemanager/`)** : L'interface utilisateur est construite en JavaFX avec des fichiers FXML modulaires situés dans le dossier `view/` (`main.fxml`, `filters_panel.fxml`, `library_panel.fxml`). Le style est géré via la feuille CSS dans `style/style.css`.
*   **Contrôleur (`src/main/java/com/imagemanager/controller/`)** : Gère les interactions utilisateur, réparti de manière experte entre :
    *   `MainController` (gestion de l'image et liens entre les composants)
    *   `FilterController` (gestion des actions liées aux filtres et transformations)
    *   `LibraryController` (gestion et recherche intra-bibliothèque)
*   **Services (`src/main/java/com/imagemanager/service/`)** : Isole la logique métier (ImageService, PersistenceService, TagService) pour éviter de surcharger les contrôleurs.
*   **Utilitaires (`src/main/java/com/imagemanager/util/`)** : Outillage global du projet comme SaveManager par exemple.
*   **Point d'entrée** : À la racine de `src/main/java/com/imagemanager/`, vous trouverez `Main.java`, la classe principale de l'Application JavaFX.

## Prérequis

*   Java JDK 21 (ou supérieur)
*   Maven 3.x

## Compilation et Exécution

Ouvrez un terminal à la racine du projet (là où se trouve le `pom.xml`) et exécutez la commande suivante :

```bash
mvn clean javafx:run
```