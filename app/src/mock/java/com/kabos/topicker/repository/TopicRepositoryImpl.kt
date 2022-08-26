package com.kabos.topicker.repository

import com.kabos.topicker.model.data.Topic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Math.random

class TopicRepositoryImpl(): TopicRepository {

    private val _topics: MutableStateFlow<List<Topic>> = MutableStateFlow(listOf(
        Topic(1,"sample1", ""),
        Topic(2,"sample2", ""),
    ))
    override val topics: StateFlow<List<Topic>>
        = _topics

    override suspend fun getTopic(): Topic {
        val id = (1..10).random()
        val topics = listOf(
            Topic(id,"sample1", ""),
            Topic(id,"sample2", ""),
            Topic(id,"sample3", ""),
            Topic(id,"sample4", ""),
            Topic(id,"sample5", ""),
            Topic(id,"sample6", ""),
            Topic(id,"sample7", ""),
            Topic(id,"sample8", ""),
            Topic(id,"sample9", ""),
            Topic(id,"sample10", ""),
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
