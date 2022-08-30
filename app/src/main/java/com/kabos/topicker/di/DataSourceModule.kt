package com.kabos.topicker.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.kabos.topicker.model.dao.UserDao
import com.kabos.topicker.model.db.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {
    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore =
        Firebase.firestore.apply {
            firestoreSettings = firestoreSettings {
                //オフラインキャッシュの有効化
                isPersistenceEnabled = true
            }
        }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): UserDatabase =
        Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "user_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideUserDao(db: UserDatabase): UserDao =
        db.userDao()
}
