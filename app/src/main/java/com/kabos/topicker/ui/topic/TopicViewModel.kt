package com.kabos.topicker.ui.topic

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.topicker.model.domain.TopicUiState
import com.kabos.domain.repository.TopicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@Stable
@HiltViewModel
class TopicViewModel @Inject constructor(
    private val topicRepository: com.kabos.domain.repository.TopicRepository
) : ViewModel() {

    // TODO Result.Loading入れとくのが望ましいな
    private val _topicUiState: MutableStateFlow<List<TopicUiState>> = MutableStateFlow(listOf())
    val topicUiState: StateFlow<List<TopicUiState>> = _topicUiState

    init {
        viewModelScope.launch {
            topicRepository.topics
                .collect { topics ->
                    _topicUiState.value = topics.map { TopicUiState.of(it) }
                    Timber.d("--ss collect ${topics.map { it.title }}/ ${topics.map { it.mainColor }}")
                }
        }
    }

    fun addTopic() = viewModelScope.launch {
        topicRepository.addTopic()
    }

    fun updateConversationState(id: Int, isFavorite: Boolean) = viewModelScope.launch{
        topicRepository.updateConversationState(id, isFavorite)
    }
}
