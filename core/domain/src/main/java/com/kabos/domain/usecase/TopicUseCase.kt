package com.kabos.domain.usecase

import com.kabos.domain.repository.TopicRepository
import com.kabos.model.OwnTopic
import com.kabos.model.Topic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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

    private val _screenTopics: MutableStateFlow<List<Topic>> = MutableStateFlow(listOf())
    val screenTopics: StateFlow<List<Topic>> = _screenTopics

    // todo 初期sample入れないとクラッシュしそう
    private var _ownTopics: StateFlow<List<OwnTopic>> = MutableStateFlow(listOf())
    val ownTopics: StateFlow<List<OwnTopic>> = _ownTopics

    init {
        ioScope.launch {
            _ownTopics = topicRepository.getOwnTopics().stateIn(ioScope)
            // いる？
            _ownTopics.collect()
        }
    }

    /**
     * 画面にランダムなtopicを追加する
     * ownTopicに含まれている場合、その情報を付与して画面に追加する
     * ownTopicに含まれていない場合、収集済みとしてownTopicに追加する
     * */
    suspend fun addScreenTopics() {
        val topic = topicRepository.getTopic(RANGE.random()) ?: return
        if (!isOwnTopicsContain(topic)) {
            topicRepository.addOwnTopic(OwnTopic(topic.id, false))
        }
        _screenTopics.emit(_screenTopics.value + topic)
    }

    private fun isOwnTopicsContain(topic: Topic): Boolean {
        return ownTopics.value.find { it.topicId == topic.id } != null
    }
}
