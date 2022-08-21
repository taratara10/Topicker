package com.kabos.topicker.repository

import com.kabos.topicker.model.data.Topic

interface TopicRepository {

    suspend fun getTopic():Topic

}
