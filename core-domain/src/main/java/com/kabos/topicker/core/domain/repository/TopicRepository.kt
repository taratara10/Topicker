package com.kabos.topicker.core.domain.repository

import com.kabos.topicker.core.model.OwnTopic
import kotlinx.coroutines.flow.Flow

interface TopicRepository {

    /**
     * @return ユーザーが閲覧済みのトピックすべて
     * */
    suspend fun getOwnTopicsStream(): Flow<List<OwnTopic>>

    suspend fun addOwnTopicIfNotRegistered(topicId: Int)

    suspend fun updateOwnTopicsFavoriteState(topicId: Int, isFavorite: Boolean)
}
