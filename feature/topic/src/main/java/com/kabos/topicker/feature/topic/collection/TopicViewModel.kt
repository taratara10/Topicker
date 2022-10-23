package com.kabos.topicker.feature.topic.collection

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.topicker.core.domain.usecase.TopicUseCase
import com.kabos.topicker.core.model.OwnTopic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
@HiltViewModel
class TopicViewModel @Inject constructor(
    private val topicUseCase: TopicUseCase
) : ViewModel() {

    private val _topicUiState: MutableStateFlow<TopicUiState> = MutableStateFlow(TopicUiState())
    val topicUiState: StateFlow<TopicUiState> = _topicUiState

    init {
        viewModelScope.launch {
            topicUseCase.screenTopics.collect { topics ->
                _topicUiState.emit(TopicUiState(topics))
            }
        }
    }

    fun addTopic() = viewModelScope.launch {
        topicUseCase.addScreenTopics()
    }

    fun updateConversationState(id: Int, isFavorite: Boolean) = viewModelScope.launch{
        topicUseCase.updateFavoriteState(id, isFavorite)
    }
}

/**
 * @param screenTopics 画面に表示するトピック
 * */
data class TopicUiState(
    val screenTopics: List<OwnTopic> = listOf()
)
