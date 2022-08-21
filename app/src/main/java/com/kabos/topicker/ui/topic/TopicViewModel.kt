package com.kabos.topicker.ui.topic

import androidx.lifecycle.ViewModel
import com.kabos.topicker.repository.TopicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    topicRepository: TopicRepository
): ViewModel() {

}
