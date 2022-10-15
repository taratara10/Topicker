package com.kabos.domain.repository

import com.kabos.model.OwnTopic
import com.kabos.model.Topic
import kotlinx.coroutines.flow.Flow

interface TopicRepository {

    suspend fun getTopicById(id: Int): Topic?

    suspend fun getOwnTopics(): Flow<List<OwnTopic>>

    suspend fun addOwnTopic(ownTopic: OwnTopic)

    suspend fun updateOwnTopicsFavoriteState(topicId: Int, isFavorite: Boolean)
}
