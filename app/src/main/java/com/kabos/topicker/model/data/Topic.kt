package com.kabos.topicker.model.data

import com.kabos.topicker.model.domain.ConversationState

// todo colorのmapperをどっかで書きたい String -> Color
data class Topic(
    val id: Int,
    val title: String,
    val mainColor: String,
    val conversationState: ConversationState = ConversationState.UnSelected,
) {
    fun updateConversationState(state: ConversationState): Topic =
        Topic(
            id = id,
            title = title,
            mainColor = mainColor,
            conversationState = state,
        )
}
