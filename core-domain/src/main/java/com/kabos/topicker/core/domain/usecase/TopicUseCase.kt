package com.kabos.topicker.core.domain.usecase

import com.kabos.topicker.core.domain.repository.TopicRepository
import com.kabos.topicker.core.model.OwnTopic
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

    suspend fun updateFavoriteState(topicId: Int, isFavorite: Boolean) {
        topicRepository.updateOwnTopicsFavoriteState(topicId, isFavorite)
    }

}
