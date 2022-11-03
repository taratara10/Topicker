package com.kabos.topicker.feature.topic.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.topicker.core.domain.usecase.TopicUseCase
import com.kabos.topicker.core.model.OwnTopic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val topicUseCase: TopicUseCase,
) : ViewModel() {

    companion object {
        /** topicのid範囲 ver1.0では360個 */
        const val NUMBER_OF_TOPICS = 360

        /** 1ページ目に表示するトピック */
        val TUTORIAL = listOf(OwnTopic(9999, " Let's go! \uD83D\uDC49", false))
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
     * todo errorの通知はどうするか runCatchingするかどうか
     * */
    private fun initTopicUiState() = viewModelScope.launch {
        screenTopicIds.combine(topicUseCase.getOwnTopics()) { screenTopicIds, ownTopics ->
            val result = screenTopicIds.mapNotNull { id ->
                ownTopics.find { it.topicId == id }
            }
            if (result.isEmpty()) {
                addScreenTopicId()
                TopicUiState.Loading
            } else {
                TopicUiState.Success(TUTORIAL + result)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TopicUiState.Loading,
        ).collect {
            _topicUiState.value = it
            if (it is TopicUiState.Success) {
                Timber.d("-- ${it.screenTopics.map { it.title }}")
            }
        }
    }

    fun addTopic() = viewModelScope.launch {
        val addTopicId = addScreenTopicId()
        topicUseCase.addOwnTopicIfNeeded(addTopicId, )
    }

    fun updateFavoriteState(id: Int, isFavorite: Boolean) = viewModelScope.launch {
        topicUseCase.updateFavoriteState(id, isFavorite)
    }

    private fun addScreenTopicId(): Int {
        /* (0..360).random()だと毎回固定値になってしまう */
        val randomId = (System.currentTimeMillis() % NUMBER_OF_TOPICS).toInt()
        screenTopicIds.value += randomId
        return randomId
    }
}

sealed interface TopicUiState {
    data class Success(val screenTopics: List<OwnTopic>) : TopicUiState
    object Loading : TopicUiState
    object Error : TopicUiState
}
