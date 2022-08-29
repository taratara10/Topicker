package com.kabos.topicker.model.data

import com.google.firebase.firestore.DocumentSnapshot
import com.kabos.topicker.model.domain.ConversationState
import timber.log.Timber

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

    companion object {

        fun DocumentSnapshot.toTopic(): Topic? {
            return try {
                Topic(
                    id = getLong("id")?.toInt()!!,
                    title = "${getString("title")!!}話",
                    mainColor = "",
                    conversationState = ConversationState.UnSelected,
                )
            } catch (e: Exception) {
                Timber.e("toTopic convert error. $data")
                null
            }
        }
    }
}
