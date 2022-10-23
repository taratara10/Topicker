package com.kabos.topicker.core.data.repository_impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kabos.datastore.UserDataStore
import com.kabos.topicker.core.data.extension.toOwnTopic
import com.kabos.topicker.core.data.extension.toTopic
import com.kabos.topicker.core.domain.repository.TopicRepository
import com.kabos.topicker.core.model.OwnTopic
import com.kabos.topicker.core.model.Topic
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class TopicRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val userDataStore: UserDataStore,
) : TopicRepository {

    companion object {
        const val TOPICS = "topics"
        const val USERS = "users"
        const val OWN_TOPICS = "ownTopics"
    }

    override suspend fun getTopicById(id: Int): Topic? {
        return firestore.collection(TOPICS)
            .document(id.toString())
            .get()
            .await()
            .toTopic()
    }

    override suspend fun getOwnTopics(): Flow<List<OwnTopic>> = callbackFlow {
        firestore.collection(USERS)
            .document(getUuid())
            .collection(OWN_TOPICS)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    cancel(CancellationException(error))
                }

                val result = snapshot?.documents?.mapNotNull { it.toOwnTopic() } ?: listOf()
                trySend(result)
            }
        awaitClose { channel.close() }
    }

    override suspend fun addOwnTopic(ownTopic: OwnTopic) {
        firestore.collection(USERS)
            .document(getUuid())
            .collection(OWN_TOPICS)
            .document(ownTopic.topicId.toString())
            .set(ownTopic)
    }

    private suspend fun getUuid(): String {
        return userDataStore.getUuid().first()
    }

    override suspend fun updateOwnTopicsFavoriteState(topicId: Int, isFavorite: Boolean) {
        firestore.collection(USERS)
            .document(getUuid())
            .collection(OWN_TOPICS)
            .document(topicId.toString())
            .update("isFavorite", isFavorite)
    }
}
