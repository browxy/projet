# Image Manager - Bibliothèque d'Images

Projet POO Java 2025-2026

## 📋 Description

Application JavaFX de gestion de bibliothèque d'images avec système de tags, filtres avancés et chiffrement sécurisé.

## ✨ Fonctionnalités

### 🖼️ Gestion d'Images
- Chargement d'images depuis le système de fichiers
- Affichage en temps réel
- Bibliothèque centralisée avec métadonnées

### 🏷️ Système de Tags
- Ajout de tags personnalisés
- Recherche d'images par tags
- Affichage des tags associés

### 🎨 Filtres
- **Grayscale** : Conversion en niveaux de gris
- **Sepia** : Effet sépia
- **RGB Swap** : Échange des composantes RGB → GBR
- **Prewitt** : Détection de contours
- **Rotation** : Rotation droite/gauche (90°)
- **Symétrie** : Miroir horizontal/vertical

### 🔐 Sécurité
- **Chiffrement** : Mélange sécurisé des pixels avec SHA-256 + SecureRandom
- **Déchiffrement** : Restauration avec le mot de passe correct

### 💾 Persistance
- Sauvegarde JSON avec Jackson
- Métadonnées : nom, tags, filtres, dimensions, dates
- Bibliothèque complète dans `library.json` (créé/lu dans le répertoire d’exécution de l’application)

## 🏗️ Architecture

Le projet suit une architecture **MVC** avec une séparation claire entre :
- **Modèle** : données + logique de traitement (filtres, bibliothèque, métadonnées)
- **Vue** : interface JavaFX (FXML + CSS)
- **Contrôleurs** : gestion des événements UI et coordination avec les services
- **Services** : logique métier (chargement, tags, persistance)

### Structure (réelle du projet)

```
src/
└── main/
    ├── java/
    │   ├── Main.java
    │   ├── controller/
    │   │   └── MainController.java
    │   ├── model/
    │   │   ├── Filter.java
    │   │   ├── ImageData.java
    │   │   ├── ImageLibrary.java
    │   │   ├── ImageModel.java
    │   │   └── filters/
    │   │       ├── DecryptFilter.java
    │   │       ├── EncryptFilter.java
    │   │       ├── GrayscaleFilter.java
    │   │       ├── PrewittFilter.java
    │   │       ├── RGBSwapFilter.java
    │   │       ├── RotateFilter.java
    │   │       ├── SepiaFilter.java
    │   │       └── SymmetryFilter.java
    │   ├── service/
    │   │   ├── ImageService.java
    │   │   ├── PersistenceService.java
    │   │   └── TagService.java
    │   └── util/
    │       └── SaveManager.java
    └── resources/
        ├── main.fxml
        └── style.css
```

### Flux principal

1. `Main.java` démarre JavaFX, charge `main.fxml` et applique `style.css`.
2. `main.fxml` est relié au contrôleur `MainController` et mappe les actions des boutons vers ses méthodes (`loadImage`, `applyGrayscale`, `saveToLibrary`, etc.).
3. Au démarrage (`initialize()`), `MainController` charge automatiquement la bibliothèque via `PersistenceService.loadLibrary()` puis met à jour la `ListView`.
4. Le contrôleur délègue la logique métier aux **services** (ImageService/TagService/PersistenceService) et manipule les **modèles** (`ImageModel`, `ImageLibrary`, `ImageData`).
5. La persistance JSON est gérée par :
   - `PersistenceService` (conversion / orchestration)
   - `SaveManager` (lecture/écriture JSON via Jackson)
   et utilise `library.json` dans le répertoire d’exécution.

## 🛠️ Technologies

- **JavaFX 23** : Interface graphique
- **Jackson 2.17** : Sérialisation JSON
- **Maven** : Gestion des dépendances
- **Java 21** : Langage

## 🚀 Installation

### Prérequis
- JDK 21
- Maven
- JavaFX SDK 23

### Compilation
```bash
mvn clean compile
```

### Exécution
```bash
mvn javafx:run
```

## 📖 Utilisation

### 1. Charger une image
Cliquez sur **"Charger image"** et sélectionnez un fichier

### 2. Appliquer des filtres
Utilisez les boutons dans la section **FILTRES**

### 3. Ajouter des tags
- Tapez un tag dans le champ texte
- Cliquez sur **"Add Tag"**

### 4. Sauvegarder dans la bibliothèque
Cliquez sur **"Save to Library"**

### 5. Rechercher par tag
- Tapez un tag dans le champ de recherche
- Cliquez sur **"Search"**

### 6. Chiffrer/Déchiffrer
- **Encrypt** : Entrez un mot de passe pour chiffrer
- **Decrypt** : Utilisez le même mot de passe pour déchiffrer

## 🔒 Sécurité

### Algorithme de chiffrement
1. Hash du mot de passe avec **SHA-256**
2. Génération de seed pour **SecureRandom (SHA1PRNG)**
3. Permutation déterministe des pixels
4. Déchiffrement via permutation inverse

## 📦 Dépendances

```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>23.0.2</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>23.0.2</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.17.0</version>
    </dependency>
</dependencies>
```
