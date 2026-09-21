package com.example.data.repository

import com.example.data.local.InitialData
import com.example.data.local.OtakuDao
import com.example.data.model.ClubEntity
import com.example.data.model.CommentEntity
import com.example.data.model.EventEntity
import com.example.data.model.LeaderboardUser
import com.example.data.model.NotificationItemEntity
import com.example.data.model.PostEntity
import com.example.data.model.QuizHistoryEntity
import com.example.data.model.QuizQuestionEntity
import com.example.data.model.UserProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

data class XpRewardResult(
    val amount: Int,
    val didLevelUp: Boolean,
    val newLevel: Int,
    val newRank: String
)

class OtakuRepository(private val dao: OtakuDao) {

    val userProfile: Flow<UserProfileEntity> = dao.getUserProfile().map { it ?: InitialData.initialProfile }
    val allClubs: Flow<List<ClubEntity>> = dao.getAllClubs()
    val allPosts: Flow<List<PostEntity>> = dao.getAllPosts()
    val allQuestions: Flow<List<QuizQuestionEntity>> = dao.getAllQuestions()
    val allQuizHistory: Flow<List<QuizHistoryEntity>> = dao.getQuizHistory()
    val allEvents: Flow<List<EventEntity>> = dao.getAllEvents()
    val allBadges = dao.getAllBadges()
    val allNotifications: Flow<List<NotificationItemEntity>> = dao.getAllNotifications()

    fun getCommentsForPost(postId: Int): Flow<List<CommentEntity>> {
        return dao.getCommentsForPost(postId)
    }

    suspend fun addComment(postId: Int, content: String, authorName: String = "Sacha", authorAvatar: String = "⚡") {
        val comment = CommentEntity(
            postId = postId,
            authorName = authorName,
            authorAvatar = authorAvatar,
            content = content.trim(),
            timestampStr = "À l'instant"
        )
        dao.insertComment(comment)

        // Increment commentsCount in post
        val posts = dao.getAllPosts().firstOrNull() ?: emptyList()
        val post = posts.find { it.id == postId }
        if (post != null) {
            dao.updatePost(post.copy(commentsCount = post.commentsCount + 1))
        }

        // Add 15 XP for community contribution
        addXp(15)
    }

    suspend fun addQuizQuestion(
        universe: String,
        difficulty: String,
        question: String,
        opt1: String,
        opt2: String,
        opt3: String,
        opt4: String,
        correctIndex: Int,
        explanation: String
    ) {
        val xp = when (difficulty) {
            "Débutant" -> 25
            "Intermédiaire" -> 50
            "Expert" -> 80
            else -> 120
        }
        val newQuestion = QuizQuestionEntity(
            universe = universe.trim().ifBlank { "Général Anime" },
            difficulty = difficulty,
            question = question.trim(),
            option1 = opt1.trim(),
            option2 = opt2.trim(),
            option3 = opt3.trim(),
            option4 = opt4.trim(),
            correctOptionIndex = correctIndex,
            explanation = explanation.trim().ifBlank { "Question soumise par un membre de la communauté Otaku Hub !" },
            xpReward = xp
        )
        dao.insertQuizQuestion(newQuestion)
        // Creator reward
        addXp(50)
    }

    suspend fun addAdminQuizQuestion(
        universe: String,
        difficulty: String,
        question: String,
        opt1: String,
        opt2: String,
        opt3: String,
        opt4: String,
        correctIndex: Int,
        explanation: String,
        xp: Int = 50,
        adminAuthor: String = "Admin Otaku"
    ) {
        val newQuestion = QuizQuestionEntity(
            universe = universe.trim().ifBlank { "Général Anime" },
            difficulty = difficulty,
            question = question.trim(),
            option1 = opt1.trim(),
            option2 = opt2.trim(),
            option3 = opt3.trim(),
            option4 = opt4.trim(),
            correctOptionIndex = correctIndex,
            explanation = explanation.trim().ifBlank { "Explication officielle certifiée par les administrateurs d'Otaku Hub." },
            xpReward = xp,
            createdByAdmin = true,
            adminAuthor = adminAuthor.trim().ifBlank { "Admin Otaku" }
        )
        dao.insertQuizQuestion(newQuestion)
        addXp(60)
    }

    suspend fun deleteQuizQuestion(id: Int) {
        dao.deleteQuizQuestion(id)
    }

    suspend fun fetchAnimeTriviaQuestions(seriesFilter: String? = null): Int {
        val existing = dao.getAllQuestions().firstOrNull() ?: emptyList()
        val existingTexts = existing.map { it.question.trim().lowercase() }.toSet()

        val pool = InitialData.popularTriviaPool.filter { item ->
            if (seriesFilter.isNullOrBlank() || seriesFilter == "Tous") true
            else item.universe.contains(seriesFilter, ignoreCase = true)
        }

        var inserted = 0
        for (q in pool) {
            if (!existingTexts.contains(q.question.trim().lowercase())) {
                dao.insertQuizQuestion(q)
                inserted++
            }
        }
        return inserted
    }

    suspend fun recordQuizScoreAndReputation(
        animeSeries: String,
        questionSummary: String,
        isCorrect: Boolean,
        xpReward: Int,
        reputationReward: Int
    ): XpRewardResult {
        // Record in quiz history
        val now = java.text.SimpleDateFormat("dd/MM HH:mm", java.util.Locale.FRANCE).format(java.util.Date())
        val historyEntry = QuizHistoryEntity(
            animeSeries = animeSeries,
            questionSummary = questionSummary.take(100),
            isCorrect = isCorrect,
            pointsEarned = if (isCorrect) reputationReward else 10,
            timestampStr = "Aujourd'hui à $now"
        )
        dao.insertQuizHistory(historyEntry)

        // Update profile reputation
        val profile = dao.getUserProfile().firstOrNull() ?: InitialData.initialProfile
        val newRepPoints = (profile.reputationPoints + if (isCorrect) reputationReward else 10).coerceAtLeast(0)
        val newTier = when {
            newRepPoints >= 3500 -> "Sage Légendaire d'Akihabara 👑"
            newRepPoints >= 2000 -> "Maître des Univers 🔮"
            newRepPoints >= 1000 -> "Érudit Otaku ⚔️"
            newRepPoints >= 500 -> "Apprenti Otaku 📜"
            else -> "Initié Anime 🍃"
        }
        val updatedProfile = profile.copy(
            reputationPoints = newRepPoints,
            reputationTier = newTier
        )
        dao.insertOrUpdateProfile(updatedProfile)

        return addXpAndProgress(xpReward, addQuiz = true, addWin = false)
    }

    suspend fun ensureDataSeeded() {
        val currentProfile = dao.getUserProfile().firstOrNull()
        if (currentProfile == null) {
            dao.insertOrUpdateProfile(InitialData.initialProfile)
            dao.insertClubs(InitialData.initialClubs)
            dao.insertPosts(InitialData.initialPosts)
            dao.insertComments(InitialData.initialComments)
            dao.insertQuizQuestions(InitialData.initialQuestions)
            dao.insertEvents(InitialData.initialEvents)
            dao.insertBadges(InitialData.initialBadges)
            dao.insertNotifications(InitialData.initialNotifications)
            for (h in InitialData.initialQuizHistory) {
                dao.insertQuizHistory(h)
            }
        } else {
            // Guarantee all master-level deep-lore questions are synced in the database
            dao.insertQuizQuestions(InitialData.initialQuestions)
            val history = dao.getQuizHistory().firstOrNull()
            if (history.isNullOrEmpty()) {
                for (h in InitialData.initialQuizHistory) {
                    dao.insertQuizHistory(h)
                }
            }
        }
    }

    suspend fun toggleLikePost(post: PostEntity) {
        val newIsLiked = !post.isLiked
        val newCount = if (newIsLiked) post.likesCount + 1 else (post.likesCount - 1).coerceAtLeast(0)
        dao.updatePost(post.copy(isLiked = newIsLiked, likesCount = newCount))
    }

    suspend fun addPost(content: String, category: String, clubName: String, authorName: String = "Sacha") {
        val newPost = PostEntity(
            authorName = authorName,
            authorAvatar = "⚡",
            authorRank = "Elite Otaku",
            clubName = clubName,
            content = content,
            category = category,
            timestampStr = "À l'instant",
            likesCount = 1,
            commentsCount = 0,
            isLiked = true
        )
        dao.insertPost(newPost)
        // Gain 10 XP for participating in community discussions
        addXp(10)
    }

    suspend fun toggleJoinClub(club: ClubEntity) {
        val newJoined = !club.isJoined
        val newCount = if (newJoined) club.memberCount + 1 else (club.memberCount - 1).coerceAtLeast(1)
        val newRole = if (newJoined) "Membre" else "Visiteur"
        dao.updateClub(club.copy(isJoined = newJoined, memberCount = newCount, userRole = newRole))

        // Update profile clubs count
        val profile = dao.getUserProfile().firstOrNull() ?: InitialData.initialProfile
        val newClubsCount = if (newJoined) profile.joinedClubsCount + 1 else (profile.joinedClubsCount - 1).coerceAtLeast(0)
        dao.insertOrUpdateProfile(profile.copy(joinedClubsCount = newClubsCount))
    }

    suspend fun createClub(name: String, banner: String, universe: String, description: String, tags: String) {
        val newClub = ClubEntity(
            name = name,
            bannerEmoji = banner.ifBlank { "🏯" },
            animeUniverse = universe.ifBlank { "Anime" },
            presidentName = "Sacha",
            memberCount = 1,
            level = 1,
            description = description,
            userRole = "Président",
            isJoined = true,
            tags = tags.ifBlank { "Nouveau Club, Communauté" }
        )
        dao.insertClub(newClub)
        val profile = dao.getUserProfile().firstOrNull() ?: InitialData.initialProfile
        dao.insertOrUpdateProfile(profile.copy(joinedClubsCount = profile.joinedClubsCount + 1))
        addXp(100)
    }

    suspend fun toggleRegisterEvent(event: EventEntity) {
        val newRegistered = !event.isRegistered
        val newCount = if (newRegistered) event.participantsCount + 1 else (event.participantsCount - 1).coerceAtLeast(0)
        dao.updateEvent(event.copy(isRegistered = newRegistered, participantsCount = newCount))
    }

    suspend fun recordQuizCompletion(xpGained: Int): XpRewardResult {
        return addXpAndProgress(xpGained, addQuiz = true, addWin = false)
    }

    suspend fun recordBattleResult(won: Boolean, xpGained: Int): XpRewardResult {
        return addXpAndProgress(xpGained, addQuiz = false, addWin = won)
    }

    suspend fun addXp(amount: Int): XpRewardResult {
        return addXpAndProgress(amount, addQuiz = false, addWin = false)
    }

    private suspend fun addXpAndProgress(amount: Int, addQuiz: Boolean, addWin: Boolean): XpRewardResult {
        val profile = dao.getUserProfile().firstOrNull() ?: InitialData.initialProfile
        var currentXp = profile.currentXp + amount
        var level = profile.level
        val initialLevel = profile.level
        var maxXp = profile.maxXp

        while (currentXp >= maxXp) {
            currentXp -= maxXp
            level += 1
            maxXp = (maxXp * 1.25f).toInt()
        }

        val rankTitle = when {
            level >= 50 -> "Légende Otaku 👑"
            level >= 20 -> "Maître Otaku ⭐"
            level >= 15 -> "Elite Otaku ⚡"
            level >= 10 -> "Otaku Confirmé 🌀"
            level >= 5 -> "Fan Passionné 🍃"
            else -> "Novice Otaku 🌱"
        }

        val updated = profile.copy(
            level = level,
            currentXp = currentXp,
            maxXp = maxXp,
            rankTitle = rankTitle,
            quizzesSolved = if (addQuiz) profile.quizzesSolved + 1 else profile.quizzesSolved,
            winsCount = if (addWin) profile.winsCount + 1 else profile.winsCount
        )
        dao.insertOrUpdateProfile(updated)

        return XpRewardResult(
            amount = amount,
            didLevelUp = level > initialLevel,
            newLevel = level,
            newRank = rankTitle
        )
    }

    suspend fun updateProfile(username: String, bio: String, favAnimes: String, favCharacters: String) {
        val profile = dao.getUserProfile().firstOrNull() ?: InitialData.initialProfile
        dao.insertOrUpdateProfile(
            profile.copy(
                username = username,
                bio = bio,
                favoriteAnimes = favAnimes,
                favoriteCharacters = favCharacters
            )
        )
    }

    suspend fun updateCustomization(
        username: String,
        avatarEmoji: String,
        rankTitle: String,
        bio: String,
        favAnimes: String,
        favCharacters: String,
        profileFrame: String,
        bannerTheme: String
    ) {
        val profile = dao.getUserProfile().firstOrNull() ?: InitialData.initialProfile
        dao.insertOrUpdateProfile(
            profile.copy(
                username = username,
                avatarEmoji = avatarEmoji,
                rankTitle = rankTitle,
                bio = bio,
                favoriteAnimes = favAnimes,
                favoriteCharacters = favCharacters,
                profileFrame = profileFrame,
                bannerTheme = bannerTheme
            )
        )
    }

    suspend fun markNotificationsRead() {
        dao.markAllNotificationsAsRead()
    }

    // Dynamic, accurately sorted leaderboards reflecting real user progress
    fun getLeaderboardForTab(tabIndex: Int, currentProfile: UserProfileEntity): List<LeaderboardUser> {
        val userTotalXp = (currentProfile.level * 1000) + currentProfile.currentXp

        return when (tabIndex) {
            // Global (Ranked by Total XP)
            0 -> {
                val competitors = mutableListOf(
                    Competitor("GolDRoger_99", "👑", 14820, "Niveau 34 • Club One Piece"),
                    Competitor("GojoSensei", "🔮", 12450, "Niveau 29 • Club Jujutsu"),
                    Competitor("KakashiFan", "🍃", 10980, "Niveau 26 • Club Naruto"),
                    Competitor("SpikeSpiegel", "🪐", 9840, "Niveau 22 • Club Seinen"),
                    Competitor("Rose", "🌸", 5120, "Niveau 17 • Club Jujutsu"),
                    Competitor("TanjiroBreath", "🗡️", 3980, "Niveau 16 • Club Demon Slayer"),
                    Competitor(currentProfile.username, currentProfile.avatarEmoji, userTotalXp, "Niveau ${currentProfile.level} • ${currentProfile.rankTitle}", isCurrentUser = true)
                )
                competitors.sortByDescending { it.score }
                competitors.mapIndexed { idx, c ->
                    LeaderboardUser(
                        rank = idx + 1,
                        name = c.name,
                        avatar = c.avatar,
                        scoreDisplay = "${String.format("%,d", c.score)} XP",
                        subtitle = c.subtitle,
                        isCurrentUser = c.isCurrentUser
                    )
                }
            }

            // Club (Club Rankings)
            1 -> listOf(
                LeaderboardUser(1, "Club One Piece 🌀", "🏴‍☠️", "42,300 pts", "142 membres • Rang 1 Global"),
                LeaderboardUser(2, "Club Naruto & Boruto 🍃", "🍃", "38,900 pts", "127 membres • Rang 2 Global"),
                LeaderboardUser(3, "Club Jujutsu Kaisen 🤞", "🔮", "35,400 pts", "118 membres • Rang 3 Global"),
                LeaderboardUser(4, "Club Seinen & Chef-d'œuvre 🪐", "🧠", "28,100 pts", "88 membres • Rang 4 Global"),
                LeaderboardUser(5, "Club Demon Slayer ⚔️", "🗡️", "24,500 pts", "95 membres • Rang 5 Global")
            )

            // Hebdomadaire (Weekly Activity)
            2 -> {
                val weeklyXp = (currentProfile.currentXp % 2000) + (currentProfile.quizzesSolved * 5)
                val competitors = mutableListOf(
                    Competitor("Rose", "🌸", 1420, "Cette semaine • Série de 5 victoires"),
                    Competitor("KakashiFan", "🍃", 890, "Cette semaine • 12 quiz réussis"),
                    Competitor("ZoroLost99", "🗡️", 760, "Cette semaine • 8 battles"),
                    Competitor("AikoArt", "🎨", 650, "Cette semaine • Fan Art créatif"),
                    Competitor(currentProfile.username, currentProfile.avatarEmoji, weeklyXp, "Cette semaine • En pleine progression 🔥", isCurrentUser = true)
                )
                competitors.sortByDescending { it.score }
                competitors.mapIndexed { idx, c ->
                    LeaderboardUser(
                        rank = idx + 1,
                        name = c.name,
                        avatar = c.avatar,
                        scoreDisplay = "+${String.format("%,d", c.score)} XP",
                        subtitle = c.subtitle,
                        isCurrentUser = c.isCurrentUser
                    )
                }
            }

            // Quiz (Ranked by Quizzes Solved)
            3 -> {
                val competitors = mutableListOf(
                    Competitor("SpikeSpiegel", "🧠", 342, "Précision 98.4% • Maître Savant"),
                    Competitor("Rose", "🌸", 210, "Précision 94.2% • Expert Savoir"),
                    Competitor("KakashiFan", "🍃", 148, "Précision 89.0%"),
                    Competitor("GolDRoger_99", "👑", 135, "Précision 88.5%"),
                    Competitor(currentProfile.username, currentProfile.avatarEmoji, currentProfile.quizzesSolved, "Précision 92% • Savant Shonen", isCurrentUser = true)
                )
                competitors.sortByDescending { it.score }
                competitors.mapIndexed { idx, c ->
                    LeaderboardUser(
                        rank = idx + 1,
                        name = c.name,
                        avatar = c.avatar,
                        scoreDisplay = "${c.score} quiz",
                        subtitle = c.subtitle,
                        isCurrentUser = c.isCurrentUser
                    )
                }
            }

            // Battle (Ranked by Battle Wins)
            else -> {
                val competitors = mutableListOf(
                    Competitor("GolDRoger_99", "⚔️", 88, "Ratio 85% • Champion Arena"),
                    Competitor("GojoSensei", "🔮", 74, "Ratio 82% • Imbattable"),
                    Competitor("Kenpachi99", "🔥", 28, "Ratio 70%"),
                    Competitor("TanjiroBreath", "🗡️", 19, "Ratio 68%"),
                    Competitor(currentProfile.username, currentProfile.avatarEmoji, currentProfile.winsCount, "Ratio 78% • Duelliste Elite", isCurrentUser = true)
                )
                competitors.sortByDescending { it.score }
                competitors.mapIndexed { idx, c ->
                    LeaderboardUser(
                        rank = idx + 1,
                        name = c.name,
                        avatar = c.avatar,
                        scoreDisplay = "${c.score} victoires",
                        subtitle = c.subtitle,
                        isCurrentUser = c.isCurrentUser
                    )
                }
            }
        }
    }
}

private data class Competitor(
    val name: String,
    val avatar: String,
    val score: Int,
    val subtitle: String,
    val isCurrentUser: Boolean = false
)
