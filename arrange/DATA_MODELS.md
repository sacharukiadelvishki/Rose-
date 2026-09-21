# 💾 Schéma de Base de Données & Modèles - Otaku Hub

L'application repose sur **Room Database** (SQLite optimisé avec KSP). Ce document détaille les entités, champs et rôles dans le stockage local.

---

## 1. Table `user_profile` (`UserProfileEntity`)

Stocke les informations du joueur, sa progression, son identité et sa réputation.

| Champ | Type SQLite | Description |
| :--- | :--- | :--- |
| `id` | `INTEGER PRIMARY KEY` | Identifiant unique (1 par défaut pour le profil local) |
| `username` | `TEXT` | Nom d'utilisateur affiché |
| `avatarEmoji` | `TEXT` | Émoticône ou avatar visuel sélectionné |
| `level` | `INTEGER` | Niveau global du joueur |
| `currentXp` | `INTEGER` | Points d'expérience accumulés au niveau en cours |
| `xpToNextLevel` | `INTEGER` | Seuil d'XP pour franchir le niveau suivant |
| `otakuCoins` | `INTEGER` | Devise virtuelle de la boutique |
| `rankTitle` | `TEXT` | Titre honorifique actuel |
| `quizzesSolved` | `INTEGER` | Compteur total de quiz résolus avec succès |
| `reputationPoints` | `INTEGER` | Points de réputation Otaku (RP) |
| `reputationTier` | `TEXT` | Rang de réputation (Initié, Apprenti, Érudit, etc.) |

---

## 2. Table `quiz_questions` (`QuizQuestionEntity`)

Stocke les questions de quiz, les options de réponse, les explications et les métadonnées de modération.

| Champ | Type SQLite | Description |
| :--- | :--- | :--- |
| `id` | `INTEGER PRIMARY KEY AUTOINCREMENT` | Identifiant unique de la question |
| `universe` | `TEXT` | Nom de l'anime / univers (*One Piece, Naruto...*) |
| `difficulty` | `TEXT` | Niveau (*Débutant, Intermédiaire, Expert, Maître*) |
| `question` | `TEXT` | Intitulé complet de la question |
| `option1` | `TEXT` | Première option de réponse |
| `option2` | `TEXT` | Deuxième option de réponse |
| `option3` | `TEXT` | Troisième option de réponse |
| `option4` | `TEXT` | Quatrième option de réponse |
| `correctOptionIndex` | `INTEGER` | Indice (0 à 3) de la bonne réponse |
| `explanation` | `TEXT` | Explication canonique bienveillante du lore |
| `xpReward` | `INTEGER` | Récompense en points d'XP |
| `createdByAdmin` | `INTEGER (Boolean)` | `1` si certifié par un administrateur, `0` sinon |
| `adminAuthor` | `TEXT` | Signature du créateur ou administrateur |

---

## 3. Table `quiz_history` (`QuizHistoryEntity`)

Enregistre l'historique chronologique de chaque tentative de quiz pour les statistiques.

| Champ | Type SQLite | Description |
| :--- | :--- | :--- |
| `id` | `INTEGER PRIMARY KEY AUTOINCREMENT` | Identifiant de l'entrée d'historique |
| `animeSeries` | `TEXT` | Univers anime de la question répondue |
| `questionSummary` | `TEXT` | Extrait ou résumé de la question |
| `isCorrect` | `INTEGER (Boolean)` | `1` si le joueur a trouvé la bonne réponse, sinon `0` |
| `pointsEarned` | `INTEGER` | Points de réputation (RP) remportés |
| `timestampStr` | `TEXT` | Date ou heure formatée de la session |

---

## 4. Tables Complémentaires

- **`clubs` (`ClubEntity`)** : Liste des guildes, effectifs, bannières et descriptions.
- **`events` (`EventEntity`)** : Salons, projections et rendez-vous communautaires.
- **`notifications` (`NotificationEntity`)** : Messages d'alertes, quêtes terminées et annonces système.
- **`battle_records` (`BattleRecordEntity`)** : Résultats des affrontements et votes de communauté.
