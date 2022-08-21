package com.kabos.topicker.model.domain

import androidx.compose.ui.graphics.Color

data class TopicUiState (
    val title: String,
    val color: Color,
    val conversationState: ConversationState
    )

sealed class ConversationState {
    object UnSelected: ConversationState()
    object Complete: ConversationState()
    object Skip: ConversationState()
}
