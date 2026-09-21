# 🏛️ Architecture & Conception Technique - Otaku Hub

Ce document présente l'architecture logicielle, les flux de données et les choix techniques retenus pour la réalisation de l'application **Otaku Hub**.

---

## 1. Principes Directeurs

L'application a été conçue selon les recommandations officielles d'Android pour les applications modernes :
1. **Séparation des Préoccupations (Separation of Concerns)** : Découplage strict entre la présentation graphique, la gestion des états et la source de données.
2. **Flux Unidirectionnel de Données (Unidirectional Data Flow - UDF)** : Les événements utilisateur remontent de l'interface vers le `ViewModel`, qui met à jour un `StateFlow`. L'interface réagit automatiquement aux changements d'état sans mutation directe.
3. **Persistance Locale Hors-Ligne (Offline-First)** : L'ensemble des profils, clubs, événements, quêtes et historiques de quiz est stocké localement dans une base SQLite robuste via Room.
4. **Réactivité Déclarative** : Utilisation exclusive de **Jetpack Compose (Material Design 3)** pour une interface fluide, animée et adaptative.

---

## 2. Diagramme de Flux (Architecture MVVM)

```
┌─────────────────────────────────────────────────────────────┐
│                       INTERFACE (UI)                        │
│   Composable Screens (HomeScreen, QuizScreen, Portals...)   │
└──────────────────────────▲───────────────┬──────────────────┘
                           │ (StateFlow)   │ (Actions / Clics)
                           │               ▼
┌──────────────────────────┴──────────────────────────────────┐
│                   VIEWMODEL (OtakuViewModel)                │
│    State Management, Coroutines, StateFlow, i18n, Scoring   │
└──────────────────────────▲───────────────┬──────────────────┘
                           │ (Flow)        │ (Suspend functions)
                           │               ▼
┌──────────────────────────┴──────────────────────────────────┐
│              RÉFÉRENTIEL (OtakuRepository)                  │
│       Single Source of Truth, Business Logic Orchestration   │
└──────────────────────────▲───────────────┬──────────────────┘
                           │ (Flow)        │ (Room Queries)
                           │               ▼
┌──────────────────────────┴──────────────────────────────────┐
│              COUCHE DE PERSISTANCE (Room DAO)               │
│            OtakuDao.kt  <──>  AppDatabase.kt (SQLite)       │
└─────────────────────────────────────────────────────────────┘
```

---

## 3. Détail des Composants d'Architecture

### A. La Couche Données (Data Layer)
- **`AppDatabase`** : Base de données Room annotée `@Database`, configurée avec la stratégie d'export de schéma et pré-remplissage via `InitialData.kt`.
- **`OtakuDao`** : Fournit des fonctions suspendues (`suspend fun`) pour les opérations d'écriture sécurisées et des flux réactifs (`Flow<T>`) pour l'observation continue des données.
- **`OtakuRepository`** : Fournit une abstraction propre des opérations de base de données, encapsulant l'attribution d'XP, la montée de niveau, et la persistance des résultats.

### B. La Couche Présentation (Presentation Layer)
- **`OtakuViewModel`** :
  - Centralise l'état global (`UserProfile`, `QuizPracticeState`, `SelectedTab`, `CurrentLanguage`).
  - Gère les coroutines sous `viewModelScope` avec un cycle de vie lié au composant parent.
  - Offre des méthodes idempotentes et sécurisées pour chaque interaction utilisateur.
- **`MainAppScaffold`** :
  - Intègre `enableEdgeToEdge()` pour un rendu immersif sous la barre d'état et la barre de navigation.
  - Alterne de façon fluide entre la barre de navigation inférieure (`OtakuBottomBar`) et les écrans modaux.

---

## 4. Performance & Bonnes Pratiques
- **Gestion de la mémoire** : Utilisation de `derivedStateOf` et `remember` dans les Composables pour limiter les recompositions superflues.
- **Accessibilité (a11y)** : Tailles de contact interactives d'au moins **48dp x 48dp** sur l'ensemble des boutons et icônes.
- **Sécurité** : Exclusion des clés et certificats du suivi de version Git via un fichier `.gitignore` strict.
