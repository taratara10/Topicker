package com.kabos.topicker.model.domain

import androidx.compose.ui.graphics.Color
import com.kabos.topicker.model.data.Topic
import com.kabos.topicker.ui.theme.colors

// TODO pageStateでは？？？
data class TopicUiState(
    val id: Int,
    val title: String,
    val color: Color,
    val conversationState: ConversationState
) {
   companion object {
       fun of(topic: Topic): TopicUiState =
           TopicUiState(
               id = topic.id,
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

