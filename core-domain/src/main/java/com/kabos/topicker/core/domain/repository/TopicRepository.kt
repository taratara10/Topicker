package com.kabos.topicker.core.domain.repository

import com.kabos.topicker.core.model.OwnTopic
import com.kabos.topicker.core.model.Topic
import kotlinx.coroutines.flow.Flow

interface TopicRepository {

    suspend fun getTopicById(id: Int): Topic?

    /**
     * @return ユーザーが閲覧済みのトピックすべて
     * */
    suspend fun getOwnTopics(): Flow<List<OwnTopic>>

    suspend fun addOwnTopic(ownTopic: OwnTopic)

    suspend fun updateOwnTopicsFavoriteState(topicId: Int, isFavorite: Boolean)
}
