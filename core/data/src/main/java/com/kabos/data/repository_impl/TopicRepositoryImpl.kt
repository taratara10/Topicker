package com.kabos.data.repository_impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kabos.data.extension.toOwnTopic
import com.kabos.data.extension.toTopic
import com.kabos.datastore.UserDataStore
import com.kabos.domain.repository.TopicRepository
import com.kabos.model.ConversationState
import com.kabos.model.OwnTopic
import com.kabos.model.Topic
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
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
        val RANGE = 0..360
    }

    override val topics: StateFlow<List<Topic>>
        get() = _topics
    private val _topics: MutableStateFlow<List<Topic>> = MutableStateFlow(
        listOf(
            Topic(1, "sample1", false, ""),
            Topic(2, "sample2", false, ""),
        )
    )

    override suspend fun getTopic(id: Int): Topic? {
        return firestore.collection(TOPICS)
            .document(id.toString())
            .get()
            .await()
            .toTopic()
    }

    override suspend fun updateConversationState(id: Int, isFavorite: Boolean) {
        val conversationState =
            if (isFavorite) ConversationState.Favorite else ConversationState.UnSelected
        val updateList = topics.value.map { item ->
            if (item.id == id) {
                item.updateConversationState(conversationState)
            } else {
                item
            }
        }
        _topics.emit(updateList)
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
}
