package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.BadgeEntity
import com.example.data.model.ClubEntity
import com.example.data.model.CommentEntity
import com.example.data.model.EventEntity
import com.example.data.model.NotificationItemEntity
import com.example.data.model.PostEntity
import com.example.data.model.QuizHistoryEntity
import com.example.data.model.QuizQuestionEntity
import com.example.data.model.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OtakuDao {

    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: UserProfileEntity)

    // Clubs
    @Query("SELECT * FROM clubs ORDER BY memberCount DESC")
    fun getAllClubs(): Flow<List<ClubEntity>>

    @Query("SELECT * FROM clubs WHERE id = :id LIMIT 1")
    suspend fun getClubById(id: Int): ClubEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClubs(clubs: List<ClubEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClub(club: ClubEntity)

    @Update
    suspend fun updateClub(club: ClubEntity)

    // Posts
    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)

    @Update
    suspend fun updatePost(post: PostEntity)

    // Comments
    @Query("SELECT * FROM comments WHERE postId = :postId ORDER BY id ASC")
    fun getCommentsForPost(postId: Int): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentEntity>)

    // Quiz Questions
    @Query("SELECT * FROM quiz_questions WHERE difficulty = :difficulty")
    fun getQuestionsByDifficulty(difficulty: String): Flow<List<QuizQuestionEntity>>

    @Query("SELECT * FROM quiz_questions WHERE universe = :universe")
    fun getQuestionsByUniverse(universe: String): Flow<List<QuizQuestionEntity>>

    @Query("SELECT * FROM quiz_questions")
    fun getAllQuestions(): Flow<List<QuizQuestionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizQuestions(questions: List<QuizQuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizQuestion(question: QuizQuestionEntity)

    @Query("DELETE FROM quiz_questions WHERE id = :id")
    suspend fun deleteQuizQuestion(id: Int)

    // Quiz History & Reputation Tracking
    @Query("SELECT * FROM quiz_history ORDER BY id DESC LIMIT 40")
    fun getQuizHistory(): Flow<List<QuizHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizHistory(history: QuizHistoryEntity)

    // Events
    @Query("SELECT * FROM events ORDER BY id ASC")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Update
    suspend fun updateEvent(event: EventEntity)

    // Badges
    @Query("SELECT * FROM badges")
    fun getAllBadges(): Flow<List<BadgeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBadges(badges: List<BadgeEntity>)

    @Update
    suspend fun updateBadge(badge: BadgeEntity)

    // Notifications
    @Query("SELECT * FROM notifications ORDER BY id DESC")
    fun getAllNotifications(): Flow<List<NotificationItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationItemEntity)

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllNotificationsAsRead()
}
