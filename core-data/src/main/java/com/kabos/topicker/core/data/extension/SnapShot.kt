package com.kabos.topicker.core.data.extension

import com.google.firebase.firestore.DocumentSnapshot
import com.kabos.topicker.core.model.OwnTopic
import com.kabos.topicker.core.model.Topic
import timber.log.Timber

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
            title = getString("title")!!,
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
            title = getString("title")!!,
            isFavorite = getBoolean("isFavorite") == true,
            isRegistered = getBoolean("isRegistered") == true
        )
    } catch (e: Exception) {
        Timber.e("toOwnTopic convert error. $data")
        null
    }
}
