package com.kabos.model

// todo colorのmapperをどっかで書きたい String -> Color
data class Topic(
    val id: Int,
    val title: String,
    val isFavorite: Boolean,
    val mainColor: String, // todo これいる？
    val conversationState: ConversationState = ConversationState.UnSelected,
) {
    fun updateFavorite(isFavorite: Boolean): Topic =
        Topic(
            id = id,
            title = title,
            isFavorite = isFavorite,
            mainColor = mainColor,
            conversationState = conversationState
        )
}

sealed class ConversationState {
    object UnSelected : ConversationState()
    object Favorite : ConversationState()
}
