package com.kabos.topicker.core.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.kabos.topicker.core.data.repository_impl.TopicRepositoryImpl
import com.kabos.topicker.core.datastore.UserDataStore
import com.kabos.topicker.core.domain.repository.TopicRepository
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
        userDataStore: UserDataStore
    ): TopicRepository =
        TopicRepositoryImpl(firestore, userDataStore)

}
