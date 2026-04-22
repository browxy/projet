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
- Bibliothèque complète dans `library.json`

## 🏗️ Architecture

### MVC (Model-View-Controller)
```
src/
├── controller/
│   └── MainController.java
├── model/
│   ├── Filter.java
│   ├── ImageModel.java
│   ├── ImageData.java
│   ├── ImageLibrary.java
│   └── filters/
│       ├── GrayscaleFilter.java
│       ├── SepiaFilter.java
│       ├── RGBSwapFilter.java
│       ├── PrewittFilter.java
│       ├── RotateFilter.java
│       ├── SymmetryFilter.java
│       ├── EncryptFilter.java
│       └── DecryptFilter.java
├── service/
│   ├── ImageService.java
│   ├── TagService.java
│   └── PersistenceService.java
├── util/
│   └── SaveManager.java
└── resources/
    └── main.fxml
```

## 🛠️ Technologies

- **JavaFX 23** : Interface graphique
- **Jackson 2.17** : Sérialisation JSON
- **Maven** : Gestion des dépendances
- **Java 26** : Langage

## 🚀 Installation

### Prérequis
- JDK 26
- Maven
- JavaFX SDK 26

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



