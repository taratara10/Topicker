package com.kabos.topicker.feature.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.topicker.core.domain.repository.TopicRepository
import com.kabos.topicker.core.model.OwnTopic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val topicRepository: TopicRepository,
): ViewModel() {

    private val _collectionUiState: MutableStateFlow<CollectionUiState> =
        MutableStateFlow(CollectionUiState.Loading)
    val collectionUiState: StateFlow<CollectionUiState> = _collectionUiState.asStateFlow()

    init {
        viewModelScope.launch {
            topicRepository.getRegisteredOwnTopicStream().map {
                CollectionUiState.Success(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CollectionUiState.Loading,
            ).collect {
                _collectionUiState.value = it
            }
        }
    }

    fun updateFavoriteState(id: Int, isFavorite: Boolean) = viewModelScope.launch {
        topicRepository.updateOwnTopicFavoriteState(id, isFavorite)
    }
}

sealed interface CollectionUiState {
    data class Success(val ownTopics: List<OwnTopic>): CollectionUiState
    object Loading : CollectionUiState
    object Error : CollectionUiState
}

