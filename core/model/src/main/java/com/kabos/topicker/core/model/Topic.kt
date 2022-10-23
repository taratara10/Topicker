package com.kabos.topicker.core.model

data class Topic(
    val id: Int,
    val title: String,
    val isFavorite: Boolean,
) {
    fun updateFavorite(isFavorite: Boolean): Topic =
        Topic(
            id = id,
            title = title,
            isFavorite = isFavorite,
        )
}

