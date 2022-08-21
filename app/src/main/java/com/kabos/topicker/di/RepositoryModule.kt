package com.kabos.topicker.di

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
    fun provideTopicRepository(): TopicRepository =
        TopicRepositoryImpl()
}
