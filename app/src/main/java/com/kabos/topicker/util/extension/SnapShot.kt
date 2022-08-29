package com.kabos.topicker.util.extension

import com.google.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot.optString(field: String, defaultValue: String): String =
    getString(field) ?: defaultValue

fun DocumentSnapshot.getList(field: String): List<String> {
    val unCastedData = get(field)
    return if (unCastedData is List<*>) unCastedData.filterIsInstance<String>()
    else listOf()
}

fun DocumentSnapshot.optInt(field: String, defaultValue: Int) =
    getLong(field)?.toInt() ?: defaultValue
