package com.kabos.topicker.core.domain.usecase

import com.kabos.topicker.core.domain.repository.TopicRepository
import com.kabos.topicker.core.model.OwnTopic
import com.kabos.topicker.core.model.Topic
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
        OwnTopic(10000, " Let's go! \uD83D\uDC49", false),
        OwnTopic(2, "sample2", false),
    )

    private val _screenTopics: MutableStateFlow<List<OwnTopic>> = MutableStateFlow(leadTopic)
    val screenTopics: StateFlow<List<OwnTopic>> = _screenTopics

    private val _ownTopics: MutableStateFlow<List<OwnTopic>> = MutableStateFlow(listOf())
    val ownTopics: StateFlow<List<OwnTopic>> = _ownTopics

    init {
        ioScope.launch {
            topicRepository.getOwnTopics().stateIn(ioScope).collect { ownTopics ->
                _ownTopics.value = ownTopics
                _screenTopics.emit(reflectStatus(screenTopics.value, ownTopics))
            }
        }
    }

    /**
     * [ownTopics]の更新内容を[screenTopics]に反映したリストを作る
     * */
    private fun reflectStatus(screenTopics: List<OwnTopic>, allOwnTopics: List<OwnTopic>): List<OwnTopic> {
        return screenTopics.map { screenTopic ->
            allOwnTopics.find { it.topicId == screenTopic.topicId } ?: screenTopic
        }
    }

    /**
     * 画面にランダムなtopicを追加する
     * ownTopicに登録されていない場合、収集済みとしてownTopicに追加する
     * */
    suspend fun addScreenTopics() {
        val topic =
            topicRepository.getTopicById(RANGE.random()) ?: throw Exception("Cannot add topics")

        val registeredOwnTopic: OwnTopic? = ownTopics.value.find { it.topicId == topic.id }

        val ownTopic: OwnTopic =
            if (registeredOwnTopic != null) {
                registeredOwnTopic
            } else {
                val newOwnTopic = topic.toOwnTopic()
                topicRepository.addOwnTopic(newOwnTopic)
                newOwnTopic
            }

        _screenTopics.emit(_screenTopics.value + ownTopic)
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
