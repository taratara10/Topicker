package com.kabos.topicker.feature.topic.collection

import androidx.compose.ui.graphics.Color
import com.kabos.model.Topic
import com.kabos.topicker.core.design.theme.*

// TODO pageStateでは？？？ これいらなくね？？？？
data class TopicUiState(
    val id: Int,
    val title: String,
    val isFavorite: Boolean,
    val color: Color,
) {
   companion object {
       fun of(topic: Topic): TopicUiState =
           TopicUiState(
               id = topic.id,
               title = topic.title,
               isFavorite = topic.isFavorite,
               color = toColor(topic.id),
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

