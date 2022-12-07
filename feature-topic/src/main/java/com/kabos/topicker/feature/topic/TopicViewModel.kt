package com.kabos.topicker.feature.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.topicker.core.domain.repository.TopicRepository
import com.kabos.topicker.core.model.OwnTopic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val topicRepository: TopicRepository,
) : ViewModel() {

    companion object {
        /** topicのid範囲 ver1.0では100個 */
        const val NUMBER_OF_TOPICS = 99

        /** 1ページ目に表示するトピック */
        val TUTORIAL = listOf(OwnTopic(9999, "各トピックについて自由に雑談しましょう！\n\uD83D\uDC49", false))
    }

    private var _topicUiState: MutableStateFlow<TopicUiState> =
        MutableStateFlow(TopicUiState.Loading)
    val topicUiState: StateFlow<TopicUiState> = _topicUiState.asStateFlow()

    /** topicScreenに表示しているtopic 1ページ目のtutorialは除く */
    private val screenTopicIds: MutableStateFlow<List<Int>> = MutableStateFlow(listOf())

    init {
        initTopicUiState()
    }

    /**
     * [screenTopicIds]の対象topicを[OwnTopic]に変換してUiStateにまとめる
     * */
    private fun initTopicUiState() = viewModelScope.launch {
        screenTopicIds.combine(topicRepository.getOwnTopicsStream()) { screenTopicIds, ownTopics ->
            val result = screenTopicIds.mapNotNull { id ->
                ownTopics.find { it.topicId == id }
            }
            /* pagerのcircleIconは次のページの色を参照するため、2つ以上の要素がないとOutOfIndexException */
            if (result.size >= 2) {
                TopicUiState.Success(TUTORIAL + result)
            } else {
                addTopic()
                TopicUiState.Loading
            }
        }.retryWhen { _, _ ->
            /* 処理に失敗したら自働でretryする */
            emit(TopicUiState.Error)
            true
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TopicUiState.Loading,
        ).collect {
            _topicUiState.value = it
        }
    }

    fun addTopic() = viewModelScope.launch {
        val addTopicId = addScreenTopicId()
        runCatching {
            topicRepository.addOwnTopicIfNotExist(addTopicId)
        }
    }

    fun updateFavoriteState(id: Int, isFavorite: Boolean) = viewModelScope.launch {
        runCatching {
            topicRepository.updateOwnTopicFavoriteState(id, isFavorite)
        }
    }

    fun registerOwnTopic(id: Int) = viewModelScope.launch {
        runCatching {
            topicRepository.registerOwnTopic(id)
        }
    }

    private fun addScreenTopicId(): Int {
        /* (0..360).random()だと毎回固定値になってしまう */
        val randomId = Random().nextInt(NUMBER_OF_TOPICS)
        screenTopicIds.value += randomId
        return randomId
    }
}

sealed interface TopicUiState {
    data class Success(val screenTopics: List<OwnTopic>) : TopicUiState
    object Loading : TopicUiState
    object Error : TopicUiState
}
