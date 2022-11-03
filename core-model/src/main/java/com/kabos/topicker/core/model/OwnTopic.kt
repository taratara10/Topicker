package com.kabos.topicker.core.model

/**
 * Userが収集したお気に入り等の情報を保持したtopic
 *
 * @param topicId [Topic]を参照するid
 * @param title topicのtitle
 * @param isFavorite お気に入りに指定しているか
 * firestoreでは、prefixにisを含む場合、JvmFieldをつける
 * */
data class OwnTopic(
    val topicId: Int,
    val title: String,
    @field:JvmField
    val isFavorite: Boolean,
)

fun Topic.toOwnTopic(): OwnTopic =
    OwnTopic(
        topicId = id,
        title = title,
        isFavorite = isFavorite
    )

val previewOwnTopics = listOf(
    OwnTopic(1, "sample1", false),
    OwnTopic(2, "sample2", false),
    OwnTopic(3, "sample3", false),
    OwnTopic(4, "sample4", false),
    OwnTopic(5, "sample5", false),
    OwnTopic(6, "sample6", false),
    OwnTopic(7, "sample7", false),
    OwnTopic(8, "sample8", false),
    OwnTopic(9, "sample9", false),
    OwnTopic(10, "sample10", false),
)
