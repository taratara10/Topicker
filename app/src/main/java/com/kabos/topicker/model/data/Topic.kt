package com.kabos.topicker.model.data

import com.kabos.topicker.model.domain.ConversationState

// todo colorのmapperをどっかで書きたい String -> Color
data class Topic(
    val id: Int,
    val title: String,
    val mainColor: String,
    val conversationState: ConversationState = ConversationState.UnSelected,
)
