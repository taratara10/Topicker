package com.kabos.topicker.ui.topic

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.topicker.model.data.Topic
import com.kabos.topicker.model.domain.ConversationState
import com.kabos.topicker.model.domain.TopicUiState
import com.kabos.topicker.repository.TopicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Math.random
import javax.inject.Inject

@Stable
@HiltViewModel
class TopicViewModel @Inject constructor(
    private val topicRepository: TopicRepository
): ViewModel() {

    private val _topics: MutableStateFlow<List<TopicUiState>> = MutableStateFlow(
        listOf(
            TopicUiState("initial", Color.LightGray, ConversationState.UnSelected),
            TopicUiState("Second", Color.Magenta, ConversationState.UnSelected),
        )
    )
    val topics: StateFlow<List<TopicUiState>> = _topics

    fun addTopic() = viewModelScope.launch {
        val page = topicRepository.getTopic().let { TopicUiState.of(it) }
        _topics.value += page
    }

}
