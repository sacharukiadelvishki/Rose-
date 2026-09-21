# ⛩️ Otaku Hub - Dossier de Présentation & Organisation (Arrange)

Bienvenue dans le dossier **`arrange/`**. Cet espace a été spécialement conçu pour présenter l'application **Otaku Hub** de façon claire, élégante et professionnelle pour votre dépôt GitHub, vos collaborateurs ou tout démonstrateur.

---

## 📂 Sommaire du Dossier `arrange/`

1. [📖 Présentation Générale](#-présentation-générale)
2. [🏛️ Architecture & Organisation des Fichiers](#️-architecture--organisation-des-fichiers)
3. [📑 Documents Détaillés](#-documents-détaillés)
4. [🎮 Parcours Utilisateur & Démonstration](#-parcours-utilisateur--démonstration)
5. [⚠️ Note Technique Importante (Environnement Android)](#️-note-technique-importante-environnement-android)

---

## 📖 Présentation Générale

**Otaku Hub** est une application Android moderne développée en **Kotlin** et **Jetpack Compose (Material Design 3)**, assistée d'une base de données locale réactive **Room**.

Elle plonge les passionnés d'anime, manga et pop-culture japonaise dans un univers néon inspiré d'**Akihabara**, combinant apprentissage pédagogique, exploration multivers et interactions sociales.

### Les 7 Piliers de l'Application :
- 🧠 **Quiz & Trivia Anime Pédagogique** : Explications de lore à chaque question, synchronisation des anime cultes (*One Piece*, *Naruto*, *Dragon Ball*, *Solo Leveling*, etc.).
- 👑 **Console d'Administration & Modération** : Droits certifiés, création de questions avec XP/RP personnalisés et outils de modération.
- 🎖️ **Progression de Réputation (RP)** : 5 rangs évolutifs (*Initié*, *Apprenti*, *Érudit*, *Maître*, *Sage Légendaire*) avec suivi des séries de victoires et historique.
- ⛩️ **Portails du Multivers (*The Gate*)** : Exploration des mondes d'anime et tableau de quêtes de rang S.
- 🛋️ **Otaku Room Personnalisable** : Décoration de chambre virtuelle, étagères de mangas, figurines et lecteur d'ambiance lofi.
- 👥 **Clubs, Guildes & Débats** : Espaces d'échanges communautaires et système de votes interactifs.
- 🌐 **Multilinguisme Réactif (i18n)** : 5 langues (Français, Anglais, Japonais, Coréen, Chinois) commutables instantanément.

---

## 🏛️ Architecture & Organisation des Fichiers

Voici la cartographie ordonnée du projet :

```
📁 Otaku-Hub/
│
├── 📁 arrange/                          # 🌟 Ce dossier : Présentation, documentation & architecture
│   ├── README.md                       # Présentation globale & guide de démonstration
│   ├── ARCHITECTURE.md                 # Spécifications MVVM, Clean Architecture & Flux de données
│   ├── FEATURE_CATALOG.md              # Catalogue détaillé des 7 fonctionnalités majeures
│   └── DATA_MODELS.md                  # Documentation du schéma de base de données Room
│
├── 📁 app/                             # 📱 Module Android Principal
│   ├── build.gradle.kts                # Dépendances (Compose M3, Room KSP, Coroutines, Coil...)
│   └── src/main/
│       ├── AndroidManifest.xml         # Déclaration des activités, thèmes et permissions
│       ├── java/com/example/
│       │   ├── MainActivity.kt         # Point d'entrée, Edge-to-Edge & initialisation
│       │   │
│       │   ├── data/                   # 💾 Couche Données (Data Layer)
│       │   │   ├── local/              # Room Database, DAO et données initiales
│       │   │   │   ├── AppDatabase.kt  # Instance de base Room SQLite
│       │   │   │   ├── OtakuDao.kt     # Requêtes Flow réactives
│       │   │   │   └── InitialData.kt  # Lore canonique et questions de départ
│       │   │   ├── model/              # Entités & Dataclasses métier
│       │   │   │   ├── Entities.kt     # UserProfile, QuizQuestion, QuizHistory...
│       │   │   │   └── NexusWorldModels.kt # Portails, Quêtes & Artefacts
│       │   │   └── repository/         # Single Source of Truth
│       │   │       └── OtakuRepository.kt # Gestionnaire centralisé des flux
│       │   │
│       │   └── ui/                     # 🎨 Couche Interface & Présentation (UI Layer)
│       │       ├── components/         # Composants réutilisables (TopBar, BottomBar, Dialogs...)
│       │       ├── i18n/               # Moteur de traduction (FR, EN, JA, KO, ZH)
│       │       ├── navigation/         # Routes déclaratives et graphe de navigation
│       │       ├── screens/            # Les 9 écrans Compose de l'application
│       │       ├── theme/              # Thème Cyberpunk Akihabara (Color, Type, Theme)
│       │       ├── MainAppScaffold.kt  # Squelette principal et gestion des fenêtres
│       │       └── OtakuViewModel.kt   # ViewModel unifié gérant les StateFlow
│       │
│       └── res/                        # 🖼️ Ressources Graphiques & Strings
│           ├── drawable/               # Bannières héroïques, logos néon et arrière-plans
│           ├── values/                 # Couleurs, styles et chaînes système
│           └── mipmap/                 # Icônes adaptatives de l'application
│
├── 📄 build.gradle.kts                 # Configuration racine Gradle
├── 📄 settings.gradle.kts              # Déclaration du projet et des dépôts
├── 📄 README.md                        # Documentation d'accueil du dépôt GitHub
└── 📄 .gitignore                       # Protection des clés et fichiers temporaires
```

---

## 📑 Documents Détaillés

Pour approfondir chaque aspect du projet, consultez les guides inclus dans ce dossier :
- **[`ARCHITECTURE.md`](./ARCHITECTURE.md)** : Guide technique complet du flux unidirectionnel de données (UDF) et de la séparation des couches.
- **[`FEATURE_CATALOG.md`](./FEATURE_CATALOG.md)** : Revue écran par écran des fonctionnalités, des interactions et des règles métier.
- **[`DATA_MODELS.md`](./DATA_MODELS.md)** : Détail des tables SQLite, clés primaires, relations et politiques de persistance.

---

## 🎮 Parcours Utilisateur & Démonstration

Si vous présentez l'application à un tiers ou sur une vidéo de démonstration :

1. **Accueil (`HomeScreen`)** :
   - Présentez le fil d'actualité anime, le défi quotidien et la bannière néon.
   - Changez la langue via le globe en haut à droite pour montrer le multilinguisme instantané.
2. **Le Quiz Otaku (`QuizScreen`)** :
   - Montrez la sélection par univers (*One Piece*, *Jujutsu Kaisen*, *Solo Leveling*...).
   - Répondez à une question : observez l'explication bienveillante du lore qui s'affiche.
   - Ouvrez la console administrateur avec le badge doré pour montrer l'outil de création et de modération.
   - Cliquez sur **« Historique »** pour afficher la barre de progression de réputation et les paliers débloqués.
3. **Les Portails du Multivers (`PortalsScreen`)** :
   - Explorez les factions de chaque monde et les quêtes de rang S.
4. **La Otaku Room (`OtakuRoomScreen`)** :
   - Montrez la personnalisation de la pièce virtuelle et l'ambiance lofi.
5. **Les Clubs & Communauté (`ClubsScreen`)** :
   - Rejoignez un club thématique et participez aux votes de débats anime.

---

## ⚠️ Note Technique Importante (Environnement Android)

Pour garantir que l'application continue de **compiler instantanément** et de s'exécuter sur **l'émulateur en ligne** ainsi que dans **Android Studio** :
- Le module d'exécution Android demeure positionné à la racine (`app/`) avec ses fichiers de build (`settings.gradle.kts` et `build.gradle.kts`).
- Ce dossier **`arrange/`** constitue la vitrine de présentation, de documentation et d'organisation structurée du projet sans perturber le moteur de compilation Gradle d'Android.
