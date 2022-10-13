package com.kabos.domain.repository

import com.kabos.model.OwnTopic
import com.kabos.model.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TopicRepository {
    // TODO should wrap Result like a sugar?
    val topics: StateFlow<List<Topic>>

    // TODO Topicを取得するためのクエリパラメーターを追加したい
    suspend fun getTopic(): Topic

    suspend fun addTopic()

    suspend fun updateConversationState(id: Int, isFavorite: Boolean)

    suspend fun getOwnTopics(): Flow<List<OwnTopic>>

}
