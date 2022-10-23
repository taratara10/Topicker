package com.kabos.topicker.core.model

data class Topic(
    val id: Int,
    val title: String,
    val isFavorite: Boolean,
)

val previewTopics = listOf(
    Topic(1, "sample1", false),
    Topic(2, "sample2", false),
    Topic(3, "sample3", false),
    Topic(4, "sample4", false),
    Topic(5, "sample5", false),
    Topic(6, "sample6", false),
    Topic(7, "sample7", false),
    Topic(8, "sample8", false),
    Topic(9, "sample9", false),
    Topic(10, "sample10", false),
)
