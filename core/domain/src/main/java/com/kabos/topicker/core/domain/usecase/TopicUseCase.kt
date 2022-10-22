package com.kabos.domain.usecase

import com.kabos.domain.repository.TopicRepository
import com.kabos.model.OwnTopic
import com.kabos.model.Topic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TopicUseCase(
    private val topicRepository: TopicRepository,
    private val ioScope: CoroutineScope
) {
    companion object {
        /** topicのid範囲 ver1.0では360個 */
        val RANGE = 0..360
    }

    private val leadTopic = listOf(
        Topic(1, "sample1", false),
        Topic(2, "sample2", false),
    )

    private val _screenTopics: MutableStateFlow<List<Topic>> = MutableStateFlow(leadTopic)
    val screenTopics: StateFlow<List<Topic>> = _screenTopics

    private val _ownTopics: MutableStateFlow<List<OwnTopic>> = MutableStateFlow(listOf())
    val ownTopics: StateFlow<List<OwnTopic>> = _ownTopics

    init {
        ioScope.launch {
            topicRepository.getOwnTopics().stateIn(ioScope).collect {
                _ownTopics.value = it
            }
        }
    }

    /**
     * 画面にランダムなtopicを追加する
     * ownTopicに含まれている場合、その情報を付与して画面に追加する
     * ownTopicに含まれていない場合、収集済みとしてownTopicに追加する
     * */
    suspend fun addScreenTopics() {
        var topic = topicRepository.getTopicById(RANGE.random()) ?: return
        if (!isOwnTopicsContain(topic)) {
            topicRepository.addOwnTopic(OwnTopic(topic.id, false))
        } else {
            val sameIdOwnTopic = ownTopics.value.find { it.topicId == topic.id } ?: return
            topic = topic.updateFavorite(sameIdOwnTopic.isFavorite)
        }
        _screenTopics.emit(_screenTopics.value + topic)
    }

    private fun isOwnTopicsContain(topic: Topic): Boolean {
        return ownTopics.value.find { it.topicId == topic.id } != null
    }

    suspend fun updateFavoriteState(topicId: Int, isFavorite: Boolean) {
        topicRepository.updateOwnTopicsFavoriteState(topicId, isFavorite)
        _screenTopics.emit(_screenTopics.value.map {
            if (it.id == topicId) it.updateFavorite(isFavorite) else it
        })
    }
}
