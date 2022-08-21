package com.kabos.topicker.repository

import com.kabos.topicker.model.data.Topic

class TopicRepositoryImpl(): TopicRepository {
    override suspend fun getTopic(): Topic {
        return Topic("test", "red")
    }
}
