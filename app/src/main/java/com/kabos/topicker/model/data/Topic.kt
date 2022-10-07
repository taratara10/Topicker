package com.kabos.topicker.model.data

// todo colorのmapperをどっかで書きたい String -> Color
data class Topic(
    val id: Int,
    val title: String,
    val mainColor: String, // todo これいる？
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

sealed class ConversationState {
    object UnSelected : ConversationState()
    object Favorite : ConversationState()
}
