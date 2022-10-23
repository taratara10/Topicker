package com.kabos.topicker.core.domain.di

import com.kabos.topicker.core.domain.usecase.TopicUseCase
import com.kabos.topicker.core.domain.repository.TopicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideTopicUseCase(
        topicRepository: TopicRepository,
        @IOScope ioScope: CoroutineScope
    ): TopicUseCase = TopicUseCase(topicRepository, ioScope)
}
