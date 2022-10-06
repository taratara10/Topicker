package com.kabos.topicker.model.domain

import androidx.compose.ui.graphics.Color
import com.kabos.topicker.model.data.Topic
import com.kabos.topicker.ui.theme.*

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
               color = toColor(topic.id),
               conversationState = topic.conversationState,
           )

       fun toColor(id: Int): Color {
           return when (id % 10) {
               1 -> LightBlue100
               2 -> Blue100
               3 -> Indigo100
               4 -> DeepOrange100
               5 -> DeepPurple100
               6 -> Pink100
               7 -> Red100
               8 -> Cyan100
               9 -> Green100
               else -> Color.White
           }
       }
   }
}

sealed class ConversationState {
    object UnSelected : ConversationState()
    object Favorite : ConversationState()
}

