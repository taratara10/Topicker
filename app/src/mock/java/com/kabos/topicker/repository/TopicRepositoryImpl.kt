package com.kabos.topicker.repository

import com.kabos.topicker.model.data.Topic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Math.random

class TopicRepositoryImpl(): TopicRepository {

    private val _topics: MutableStateFlow<List<Topic>> = MutableStateFlow(listOf(
        Topic(random().toInt(),"sample1", ""),
        Topic(random().toInt(),"sample2", ""),
    ))
    override val topics: StateFlow<List<Topic>>
        = _topics

    override suspend fun getTopic(): Topic {
        val topics = listOf(
            Topic(random().toInt(),"sample1", ""),
            Topic(random().toInt(),"sample2", ""),
            Topic(random().toInt(),"sample3", ""),
            Topic(random().toInt(),"sample4", ""),
            Topic(random().toInt(),"sample5", ""),
            Topic(random().toInt(),"sample6", ""),
            Topic(random().toInt(),"sample7", ""),
            Topic(random().toInt(),"sample8", ""),
            Topic(random().toInt(),"sample9", ""),
            Topic(random().toInt(),"sample10", ""),
        )
        return topics.random()
    }

    override suspend fun addTopic() {
        _topics.value += getTopic()
    }

    override suspend fun updateConversationState(id: Int) {
        topics.value.find { it.id == id }
    }
}
