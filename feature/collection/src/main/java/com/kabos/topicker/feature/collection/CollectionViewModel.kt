package com.kabos.topicker.feature.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.topicker.core.domain.usecase.TopicUseCase
import com.kabos.topicker.core.model.OwnTopic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val topicUseCase: TopicUseCase
): ViewModel() {
    private val _collectionUiState: MutableStateFlow<CollectionUiState> =
        MutableStateFlow(CollectionUiState(listOf()))
    val collectionUiState: StateFlow<CollectionUiState> = _collectionUiState


    init {
        viewModelScope.launch {
            topicUseCase.ownTopics.collect { ownTopics ->
                _collectionUiState.value = CollectionUiState(ownTopics)
            }
        }
    }
}

data class CollectionUiState(
    val ownTopics: List<OwnTopic>,
)
