package com.kabos.topicker.di

import com.google.firebase.firestore.FirebaseFirestore
import com.kabos.topicker.model.dao.UserDao
import com.kabos.topicker.repository.TopicRepository
import com.kabos.topicker.repository.TopicRepositoryImpl
import com.kabos.topicker.repository.UserRepository
import com.kabos.topicker.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideTopicRepository(
        firestore: FirebaseFirestore,
        userDao: UserDao,
    ): TopicRepository =
        TopicRepositoryImpl(firestore)

    @Singleton
    @Provides
    fun provideUserRepository(
        userDao: UserDao,
    ): UserRepository =
        UserRepositoryImpl(userDao)
}
