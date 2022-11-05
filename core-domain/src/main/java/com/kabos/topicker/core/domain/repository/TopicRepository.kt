package com.kabos.topicker.core.domain.repository

import com.kabos.topicker.core.model.OwnTopic
import kotlinx.coroutines.flow.Flow

interface TopicRepository {


    /**
     * @return 閲覧済みかに関わらず取得したことのあるトピック
     * */
    suspend fun getOwnTopicsStream(): Flow<List<OwnTopic>>

    /**
     * Pagerの仕様上、あらかじめまだ表示していないtopicを取得して置く必要がある
     * そのため、ownTopicとして取得できるものの中には、表示されていないもの(isRegistered =false)があるのでfilterする
     * @return ユーザーがtopicScreenで表示した閲覧済みトピック
     * */
    suspend fun getRegisteredOwnTopicStream(): Flow<List<OwnTopic>>

    suspend fun addOwnTopicIfNotRegistered(topicId: Int)

    suspend fun updateOwnTopicsFavoriteState(topicId: Int, isFavorite: Boolean)
}
