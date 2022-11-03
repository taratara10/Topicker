package com.kabos.topicker.core.domain.usecase

import com.kabos.topicker.core.domain.repository.TopicRepository
import com.kabos.topicker.core.model.OwnTopic
import com.kabos.topicker.core.model.Topic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TopicUseCase(
    private val topicRepository: TopicRepository,
    private val ioScope: CoroutineScope,
) {

    private val _ownTopics: MutableStateFlow<List<OwnTopic>> = MutableStateFlow(listOf())
    val ownTopics: StateFlow<List<OwnTopic>> = _ownTopics

    init {
        ioScope.launch {
            topicRepository.getOwnTopicsStream().stateIn(ioScope).collect { ownTopics ->
                _ownTopics.value = ownTopics
            }
        }
    }

    suspend fun getOwnTopics(): Flow<List<OwnTopic>> {
        return topicRepository.getOwnTopicsStream()
    }

    // TODO ErrorHandling
    suspend fun addOwnTopicIfNeeded(topicId: Int) {
        val topic =
            topicRepository.getTopicById(topicId) ?: throw Exception("Cannot add topics")

        val shouldRegisterAsOwnTopic = (ownTopics.value.find { it.topicId == topic.id } == null)
        if (shouldRegisterAsOwnTopic) {
            topicRepository.addOwnTopic(topic.toOwnTopic())
        }
    }

    suspend fun updateFavoriteState(topicId: Int, isFavorite: Boolean) {
        topicRepository.updateOwnTopicsFavoriteState(topicId, isFavorite)
    }

    private fun Topic.toOwnTopic(): OwnTopic =
        OwnTopic(
            topicId = id,
            title = title,
            isFavorite = isFavorite
        )
}
