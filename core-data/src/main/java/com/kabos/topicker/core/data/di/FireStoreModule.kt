package com.kabos.topicker.core.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FireStoreModule {
    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore =
        Firebase.firestore.apply {
            firestoreSettings = firestoreSettings {
                //オフラインキャッシュの有効化
                isPersistenceEnabled = true
            }
        }
}
