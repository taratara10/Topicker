package com.kabos.topicker.core.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.kabos.topicker.core.data.extension.toOwnTopic
import com.kabos.topicker.core.data.extension.toTopic
import com.kabos.topicker.core.datastore.UserDataStore
import com.kabos.topicker.core.domain.repository.TopicRepository
import com.kabos.topicker.core.model.OwnTopic
import com.kabos.topicker.core.model.Topic
import com.kabos.topicker.core.model.toOwnTopic
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class DefaultTopicRepository(
    private val firestore: FirebaseFirestore,
    private val userDataStore: UserDataStore,
) : TopicRepository {

    companion object {
        const val TOPICS = "topics"
        const val USERS = "users"
        const val OWN_TOPICS = "ownTopics"
        const val IS_FAVORITE = "isFavorite"
        const val IS_REGISTERED = "isRegistered"
    }

    override suspend fun getOwnTopicsStream(): Flow<List<OwnTopic>> = callbackFlow {
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

    override suspend fun getRegisteredOwnTopicStream(): Flow<List<OwnTopic>> = callbackFlow {
        firestore.collection(USERS)
            .document(getUuid())
            .collection(OWN_TOPICS)
            .whereEqualTo(IS_REGISTERED, true)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    cancel(CancellationException(error))
                }

                val result = snapshot?.documents?.mapNotNull { it.toOwnTopic() } ?: listOf()
                trySend(result)
            }
        awaitClose { channel.close() }
    }

    override suspend fun addOwnTopicIfNotExist(topicId: Int) {
        if (getOwnTopicById(topicId) == null) {
            val topic = getTopicById(topicId) ?: return
            addOwnTopic(topic.toOwnTopic())
        }
    }

    override suspend fun registerOwnTopic(topicId: Int) {
        firestore.collection(USERS)
            .document(getUuid())
            .collection(OWN_TOPICS)
            .document(topicId.toString())
            .update(IS_REGISTERED, true)
    }

    override suspend fun updateOwnTopicFavoriteState(topicId: Int, isFavorite: Boolean) {
        firestore.collection(USERS)
            .document(getUuid())
            .collection(OWN_TOPICS)
            .document(topicId.toString())
            .update(IS_FAVORITE, isFavorite)
    }

    private suspend fun getTopicById(id: Int): Topic? {
        return firestore.collection(TOPICS)
            .document(id.toString())
            .get()
            .await()
            .toTopic()
    }

    private suspend fun getOwnTopicById(id: Int): OwnTopic? {
        return firestore.collection(OWN_TOPICS)
            .document(id.toString())
            .get()
            .await()
            .toOwnTopic()
    }

    private suspend fun addOwnTopic(ownTopic: OwnTopic) {
        firestore.collection(USERS)
            .document(getUuid())
            .collection(OWN_TOPICS)
            .document(ownTopic.topicId.toString())
            .set(ownTopic)
    }

    private suspend fun getUuid(): String {
        return userDataStore.getUuid().first()
    }

}
