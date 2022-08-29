package com.kabos.topicker.di

import com.google.firebase.firestore.FirebaseFirestore
import com.kabos.topicker.repository.TopicRepository
import com.kabos.topicker.repository.TopicRepositoryImpl
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
    ): TopicRepository =
        TopicRepositoryImpl(firestore)
}
