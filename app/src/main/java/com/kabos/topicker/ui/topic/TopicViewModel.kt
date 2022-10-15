package com.kabos.topicker.ui.topic

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.domain.usecase.TopicUseCase
import com.kabos.topicker.model.domain.TopicUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@Stable
@HiltViewModel
class TopicViewModel @Inject constructor(
    private val topicUseCase: TopicUseCase
) : ViewModel() {

    private val _topicUiState: MutableStateFlow<List<TopicUiState>> = MutableStateFlow(listOf())
    val topicUiState: StateFlow<List<TopicUiState>> = _topicUiState

    init {
        viewModelScope.launch {
            topicUseCase.screenTopics.collect { topics ->
                _topicUiState.value = topics.map { TopicUiState.of(it) }
                    Timber.d("--ss initializer collect ${topics.map { it.title }}/ ${topics.map { it.mainColor }}")
            }
        }
    }

    fun addTopic() = viewModelScope.launch {
        topicUseCase.addScreenTopics()
    }

    fun updateConversationState(id: Int, isFavorite: Boolean) = viewModelScope.launch{
        topicUseCase.updateFavoriteState(id, isFavorite)
    }

    fun getOwnTopic() =viewModelScope.launch {
        topicUseCase.addScreenTopics()
    }
}
