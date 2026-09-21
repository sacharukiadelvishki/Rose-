package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.BadgeEntity
import com.example.data.model.ClubEntity
import com.example.data.model.CommentEntity
import com.example.data.model.EventEntity
import com.example.data.model.NotificationItemEntity
import com.example.data.model.PostEntity
import com.example.data.model.QuizHistoryEntity
import com.example.data.model.QuizQuestionEntity
import com.example.data.model.UserProfileEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserProfileEntity::class,
        ClubEntity::class,
        PostEntity::class,
        CommentEntity::class,
        QuizQuestionEntity::class,
        EventEntity::class,
        BadgeEntity::class,
        NotificationItemEntity::class,
        QuizHistoryEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun otakuDao(): OtakuDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "otaku_hub_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                val dao = getInstance(context).otakuDao()
                                populateInitialData(dao)
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private suspend fun populateInitialData(dao: OtakuDao) {
            dao.insertOrUpdateProfile(InitialData.initialProfile)
            dao.insertClubs(InitialData.initialClubs)
            dao.insertPosts(InitialData.initialPosts)
            dao.insertComments(InitialData.initialComments)
            dao.insertQuizQuestions(InitialData.initialQuestions)
            dao.insertEvents(InitialData.initialEvents)
            dao.insertBadges(InitialData.initialBadges)
            dao.insertNotifications(InitialData.initialNotifications)
        }
    }
}
