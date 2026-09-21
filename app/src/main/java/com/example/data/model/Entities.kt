package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val username: String = "Sacha",
    val tag: String = "@sacha_otaku",
    val avatarEmoji: String = "⚡",
    val rankTitle: String = "Elite Otaku",
    val level: Int = 18,
    val currentXp: Int = 3450,
    val maxXp: Int = 4000,
    val winsCount: Int = 24,
    val quizzesSolved: Int = 156,
    val joinedClubsCount: Int = 3,
    val bio: String = "Passionné de Shonen & Seinen 🌸 Toujours prêt pour un quiz ou une Anime Battle !",
    val favoriteAnimes: String = "One Piece, Naruto, Jujutsu Kaisen, Steins;Gate",
    val favoriteCharacters: String = "Roronoa Zoro, Itachi Uchiha, Satoru Gojo",
    val profileFrame: String = "CYBER_AURA",
    val bannerTheme: String = "NEBULA_PURPLE",
    val reputationPoints: Int = 1420,
    val reputationTier: String = "Érudit Otaku",
    val isAdmin: Boolean = true
)

@Entity(tableName = "clubs")
data class ClubEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val bannerEmoji: String,
    val animeUniverse: String,
    val presidentName: String,
    val memberCount: Int,
    val level: Int,
    val description: String,
    val userRole: String, // "Président", "Modérateur", "Membre", "Visiteur"
    val isJoined: Boolean,
    val tags: String
)

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val authorName: String,
    val authorAvatar: String,
    val authorRank: String,
    val clubName: String,
    val content: String,
    val category: String, // "Théorie", "Découverte", "Fan Art", "Actu", "Sondage"
    val timestampStr: String,
    val likesCount: Int,
    val commentsCount: Int,
    val isLiked: Boolean = false
)

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val postId: Int,
    val authorName: String,
    val authorAvatar: String,
    val content: String,
    val timestampStr: String
)

@Entity(tableName = "quiz_questions")
data class QuizQuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val universe: String,
    val difficulty: String, // "Débutant", "Intermédiaire", "Expert", "Maître"
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val correctOptionIndex: Int,
    val explanation: String, // Pedagogical explanation
    val xpReward: Int,
    val createdByAdmin: Boolean = false,
    val adminAuthor: String = "Admin Otaku"
)

@Entity(tableName = "quiz_history")
data class QuizHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val animeSeries: String,
    val questionSummary: String,
    val isCorrect: Boolean,
    val pointsEarned: Int,
    val timestampStr: String
)

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val hostClub: String,
    val dateDisplay: String,
    val participantsCount: Int,
    val maxParticipants: Int,
    val type: String,
    val reward: String,
    val description: String,
    val isRegistered: Boolean = false
)

@Entity(tableName = "badges")
data class BadgeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val icon: String,
    val description: String,
    val isUnlocked: Boolean,
    val unlockedDate: String = ""
)

@Entity(tableName = "notifications")
data class NotificationItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val message: String,
    val typeIcon: String,
    val timestampStr: String,
    val isRead: Boolean = false
)

data class LeaderboardUser(
    val rank: Int,
    val name: String,
    val avatar: String,
    val scoreDisplay: String,
    val subtitle: String,
    val isCurrentUser: Boolean = false
)
