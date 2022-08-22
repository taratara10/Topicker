package com.kabos.topicker.repository

import com.kabos.topicker.model.data.Topic

class TopicRepositoryImpl(): TopicRepository {
    override suspend fun getTopic(): Topic {
        val topics = listOf(
            Topic("sample1", ""),
            Topic("sample2", ""),
            Topic("sample3", ""),
            Topic("sample4", ""),
            Topic("sample5", ""),
            Topic("sample6", ""),
            Topic("sample7", ""),
            Topic("sample8", ""),
            Topic("sample9", ""),
            Topic("sample10", ""),
        )
        return topics.random()
    }
}
