package com.kabos.topicker.repository

import com.kabos.topicker.model.data.Topic

interface TopicRepository {
    // TODO Topicを取得するためのクエリパラメーターを追加したい
    suspend fun getTopic():Topic

}
