package com.kabos.data.extension

import com.google.firebase.firestore.DocumentSnapshot
import com.kabos.model.ConversationState
import com.kabos.model.Topic
import timber.log.Timber

fun DocumentSnapshot.optString(field: String, defaultValue: String): String =
    getString(field) ?: defaultValue

fun DocumentSnapshot.getList(field: String): List<String> {
    val unCastedData = get(field)
    return if (unCastedData is List<*>) unCastedData.filterIsInstance<String>()
    else listOf()
}

fun DocumentSnapshot.optInt(field: String, defaultValue: Int) =
    getLong(field)?.toInt() ?: defaultValue

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
