# ⛩️ Otaku Hub (オタクハブ)

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-7F52FF.svg?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-M3-4285F4.svg?logo=android&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Room Database](https://img.shields.io/badge/Room%20DB-KSP-00BFA5.svg?logo=sqlite&logoColor=white)](https://developer.android.com/training/data-storage/room)
[![Android SDK](https://img.shields.io/badge/Android%20SDK-36-3DDC84.svg?logo=android&logoColor=white)](https://developer.android.com)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

> **L'application Android communautaire de référence pour les passionnés d'anime, de manga et de pop-culture japonaise.**  
> *The ultimate Android anime & manga community experience featuring lore trivia, interactive clubs, multiverse portals, and Otaku Room showcases.*

---

## 🌟 Points Forts & Fonctionnalités

### 🧠 1. Moteur de Quiz & Trivia Anime Pédagogique
- **Pédagogie bienveillante** : Chaque question détaille l'explication canonique du lore pour enrichir les connaissances de l'utilisateur sans pénalité punitive.
- **Grands Univers Couverts** : *One Piece, Naruto, Dragon Ball, Jujutsu Kaisen, Solo Leveling, Attack on Titan, Bleach, Demon Slayer, Death Note, Hunter x Hunter*...
- **Paliers de Difficulté** : *Débutant*, *Intermédiaire*, *Expert*, *Maître*.
- **Synchronisation Trivia Populaire** : Bouton de synchronisation automatique de nouveaux packs de lore anime.
- **Recherche & Filtres** : Recherche textuelle instantanée et sélection par univers d'anime.

### 👑 2. Espace Administrateur des Quiz & Modération
- **Bascule Mode Admin** : Active les droits certifiés pour les créateurs de contenu.
- **Formulaire de Création Certifiée** : Ajout de questions officielles avec gains d'XP et de Réputation personnalisés, signature d'auteur et modèles pré-remplis.
- **Modération** : Filtrage dédié aux quiz admin et suppression ciblée des questions obsolètes.

### 🎖️ 3. Progression de Réputation Otaku (RP) & Historique
- **Points de Réputation (RP)** : Système distinct d'XP récompensant l'expertise selon la difficulté.
- **Rangs Évolutifs** :
  - 📜 *Initié Anime* (0 RP)
  - ⚔️ *Apprenti Otaku* (500 RP)
  - 🔮 *Érudit Otaku* (1 000 RP)
  - 👑 *Maître des Univers* (2 000 RP)
  - 🌟 *Sage Légendaire d'Akihabara* (3 500+ RP)
- **Journal Chronologique** : Dialogue d'historique complet retraçant chaque session de quiz, les gains et les pourcentages de précision.

### ⛩️ 4. Les Portails du Multivers (*The Gate*)
- **Exploration des Univers** : Passerelles vers Konoha, Grand Line, Soul Society, la Cité Sung Jin-woo, etc.
- **Tableau de Quêtes Rang S** : Missions de communauté et défis exclusifs.

### 🛋️ 5. La Otaku Room Personnalisable
- **Chambre Virtuelle** : Vitrine de figurines à collectionner, étagères de mangas et trophées débloqués.
- **Lecteur d'Ambiance Lofi** : Synthétiseur d'ambiance sonore japonaise pour étudier ou lire ses chapitres en toute sérénité.

### 👥 6. Clubs, Débats & Anime Battles
- **Guildes & Factions** : Rejoignez des clubs spécialisés (Shonen, Seinen, Isekai, Cosplay, Studio Ghibli).
- **Débats Communautaires** : Système de vote et d'arguments interactifs (*Luffy Gear 5 vs Naruto Baryon*, etc.).

### 🌐 7. Internationalisation Complète (i18n)
- Changement de langue dynamique et instantané sans redémarrage :
  - 🇫🇷 **Français**
  - 🇬🇧 **English**
  - 🇯🇵 **日本語 (Japonais)**
  - 🇰🇷 **한국어 (Coréen)**
  - 🇨🇳 **简体中文 (Chinois simplifié)**

---

## 🏛️ Architecture Technique

Le projet respecte rigoureusement les normes modernes de développement Android et de **Clean Architecture / MVVM** :

```
app/src/main/java/com/example/
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt          # Base de données Room avec migrations
│   │   ├── OtakuDao.kt             # Interface DAO réactive (Kotlin Flow)
│   │   └── InitialData.kt          # Données de démarrage immersives
│   ├── model/
│   │   ├── Entities.kt             # Entités Room (UserProfile, QuizQuestion, QuizHistory...)
│   │   └── NexusWorldModels.kt     # Modèles du Multivers et des Quêtes
│   └── repository/
│       └── OtakuRepository.kt      # Référentiel centralisé (Single Source of Truth)
├── ui/
│   ├── components/                 # Composants réutilisables (TopBar, BottomBar, Dialogs...)
│   ├── i18n/                       # Système de traduction multilingue
│   ├── navigation/                 # Navigation Compose avec routes déclaratives
│   ├── screens/                    # Écrans Compose (Quiz, Home, Portals, Clubs, Room...)
│   ├── theme/                      # Système de design Material 3 néon Akihabara
│   ├── MainAppScaffold.kt          # Scaffold principal avec gestion des insets
│   └── OtakuViewModel.kt           # ViewModel central réactif (StateFlow & Coroutines)
└── MainActivity.kt                 # Point d'entrée Android avec edge-to-edge
```

---

## 🛠️ Stack Technologique

| Composant | Technologie | Description |
| :--- | :--- | :--- |
| **Langage** | Kotlin 2.0+ | Modernité, Coroutines, StateFlow |
| **Interface UI** | Jetpack Compose (M3) | UI déclarative, Material Design 3, animations fluides |
| **Persistance** | Room Database + KSP | SQLite moderne hors-ligne avec réactivité Flow |
| **Asynchronisme** | Kotlin Coroutines & Flow | Gestion fluide des flux d'événements et de données |
| **Images** | Coil Compose | Chargement optimisé des bannières et avatars |
| **Architecture** | MVVM / Unidirectional Data Flow | Séparation stricte des responsabilités |
| **Tests** | Robolectric & Roborazzi | Tests unitaires JVM et vérification visuelle |

---

## 🚀 Installation & Exécution

### Prérequis
- **Android Studio** Ladybug / Koala ou version plus récente
- **JDK 17** ou supérieur
- **Android SDK 36** (minSdk 24, targetSdk 36)

### Lancer le projet
1. Clonez ce dépôt GitHub :
   ```bash
   git clone https://github.com/<votre-nom-utilisateur>/<votre-depot>.git
   cd <votre-depot>
   ```
2. Ouvrez le projet dans **Android Studio**.
3. Laissez Gradle synchroniser les dépendances (`build.gradle.kts`).
4. Lancez l'application sur un émulateur ou appareil physique (`Run 'app'`).

### Ligne de commande
- **Compiler l'APK de debug** :
  ```bash
  ./gradlew assembleDebug
  ```
- **Lancer les tests unitaires** :
  ```bash
  ./gradlew testDebugUnitTest
  ```

---

## 🎨 Design & Expérience Utilisateur

L'esthétique de l'application s'inspire directement des enseignes lumineuses du quartier d'**Akihabara** à Tokyo :
- **Couleurs Principales** : Cyan Néon (`#05D9E8`), Rose Cyberpunk (`#FF2A85`), Ambre/Or Réputation (`#FFD700`).
- **Fond d'écran & Surfaces** : Noir abyssal et cartes sombres (`#12111A`, `#1A1829`) assurant un contraste élevé et un confort visuel optimal.
- **Accessibilité** : Cibles tactiles minimum de 48dp, contrastes conformes aux standards WCAG AA, prise en charge complète du bord à bord (*Edge-to-Edge*).

---

## 📄 Licence
Distribué sous la licence Apache 2.0. Voir le fichier `LICENSE` pour plus de détails.
