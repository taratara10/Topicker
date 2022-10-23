package com.kabos.data.repository_impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kabos.topicker.core.domain.repository.TopicRepository
import com.kabos.topicker.core.model.OwnTopic
import com.kabos.topicker.core.model.Topic
import com.kabos.topicker.core.model.previewOwnTopics
import com.kabos.topicker.core.model.previewTopics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TopicRepositoryImpl(firestore: FirebaseFirestore) : TopicRepository {

    override suspend fun getTopicById(id: Int): Topic? =
         previewTopics.random()

    override suspend fun getOwnTopics(): Flow<List<OwnTopic>> =
        flow {
            emit(previewOwnTopics)
        }

    override suspend fun addOwnTopic(ownTopic: OwnTopic) {

    }

    override suspend fun updateOwnTopicsFavoriteState(topicId: Int, isFavorite: Boolean) {
        
    }
}
