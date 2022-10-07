package com.kabos.topicker.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.kabos.model.ConversationState
import com.kabos.model.Topic
import com.kabos.topicker.util.extension.toTopic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class TopicRepositoryImpl(
    private val firestore: FirebaseFirestore
) : TopicRepository {
    companion object {
        const val TOPICS = "topics"
        val RANGE = 0..360
    }

    private val _topics: MutableStateFlow<List<Topic>> = MutableStateFlow(
        listOf(
            Topic(1, "sample1", ""),
            Topic(2, "sample2", ""),
        )
    )

    override val topics: StateFlow<List<Topic>>
        get() = _topics

    // todo 仮置き 適切にハンドリングする
    override suspend fun getTopic(): Topic {
        return firestore.collection(TOPICS)
            .document(RANGE.random().toString())
            .get()
            .await()
            .toTopic()!!
    }

    override suspend fun addTopic() {
        _topics.emit(_topics.value + getTopic())
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
}
