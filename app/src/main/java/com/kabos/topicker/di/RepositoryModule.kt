package com.kabos.topicker.di

import com.google.firebase.firestore.FirebaseFirestore
import com.kabos.data.repository_impl.TopicRepositoryImpl
import com.kabos.data.repository_impl.UserRepositoryImpl
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
    ): com.kabos.domain.repository.TopicRepository =
        TopicRepositoryImpl(firestore)

    @Singleton
    @Provides
    fun provideUserRepository(
    ): com.kabos.domain.repository.UserRepository =
        UserRepositoryImpl()
}
