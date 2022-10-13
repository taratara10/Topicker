package com.kabos.data.extension

import com.google.firebase.firestore.DocumentSnapshot
import com.kabos.model.ConversationState
import com.kabos.model.OwnTopic
import com.kabos.model.Topic
import timber.log.Timber

fun DocumentSnapshot.optString(field: String, defaultValue: String): String =
    getString(field) ?: defaultValue

fun DocumentSnapshot.getInt(field: String): Int? =
    getLong(field)?.toInt()

inline fun <reified T> DocumentSnapshot.getList(field: String): List<T> {
    val unCastedData = get(field)
    return if (unCastedData is List<*>) unCastedData.filterIsInstance<T>()
    else listOf()
}

fun DocumentSnapshot.toTopic(): Topic? {
    return try {
        Topic(
            id = getInt("id")!!,
            title = "${getString("title")!!}話",
            mainColor = "",
            conversationState = ConversationState.UnSelected,
        )
    } catch (e: Exception) {
        Timber.e("toTopic convert error. $data")
        null
    }
}

fun DocumentSnapshot.toOwnTopic(): OwnTopic? {
    return try {
        OwnTopic(
            topicId = getInt("topicId")!!,
            isFavorite = getBoolean("isFavorite") == true
        )
    } catch (e: Exception) {
        Timber.e("toOwnTopic convert error. $data")
        null
    }
}
