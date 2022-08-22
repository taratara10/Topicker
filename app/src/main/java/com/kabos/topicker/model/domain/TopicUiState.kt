package com.kabos.topicker.model.domain

import androidx.compose.ui.graphics.Color
import com.kabos.topicker.model.data.Topic

// TODO pageStateでは？？？
data class TopicUiState(
    val title: String,
    val color: Color,
    val conversationState: ConversationState
) {
   companion object {
       fun of(topic: Topic): TopicUiState =
           TopicUiState(
               title = topic.title,
               color = colors.random(),
               conversationState = ConversationState.UnSelected,
           )
   }
}

sealed class ConversationState {
    object UnSelected : ConversationState()
    object Complete : ConversationState()
    object Skip : ConversationState()
}

// TODO 仮置き
val colors = listOf(Color.Cyan, Color.Green, Color.Yellow, Color.Blue, Color.DarkGray, Color.Magenta)
