package com.kabos.topicker.repository

import com.kabos.topicker.model.data.Topic
import kotlinx.coroutines.flow.StateFlow

interface TopicRepository {
    // TODO should wrap Result like a sugar?
    val topics: StateFlow<List<Topic>>

    // TODO Topicを取得するためのクエリパラメーターを追加したい
    suspend fun getTopic():Topic

    suspend fun addTopic()

    suspend fun updateConversationState(id: Int, isFavorite: Boolean)

}
