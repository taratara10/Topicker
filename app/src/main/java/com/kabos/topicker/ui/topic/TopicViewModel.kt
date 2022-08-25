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
import com.kabos.topicker.ui.theme.DeepOrange100
import com.kabos.topicker.ui.theme.LightBlue100
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Math.random
import javax.inject.Inject

@Stable
@HiltViewModel
class TopicViewModel @Inject constructor(
    private val topicRepository: TopicRepository
) : ViewModel() {

    // TODO Result.Loading入れとくのが望ましいな
    private val _topicUiState: MutableStateFlow<List<TopicUiState>> = MutableStateFlow(listOf())
    val topicUiState: StateFlow<List<TopicUiState>> = _topicUiState

    init {

        viewModelScope.launch {
            topicRepository.topics
                .collect { topics ->
                    _topicUiState.value = topics.map { TopicUiState.of(it) }
                }
        }
    }

    fun addTopic() = viewModelScope.launch {
        topicRepository.addTopic()
    }

    fun updateConversationState() {
        // repositoryへリソースを更新したい
        // viewModelでリストをもつと、管理が大変、repositoryからFlowで流れてきてほしい。
    }

}
