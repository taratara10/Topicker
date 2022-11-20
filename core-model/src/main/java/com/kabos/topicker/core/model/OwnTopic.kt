package com.kabos.topicker.core.model

/**
 * Userが収集したお気に入り等の情報を保持したtopic
 *
 * @param topicId [Topic]を参照するid
 * @param title topicのtitle
 * @param isFavorite お気に入りに指定しているか
 * @param isRegistered Topic画面で見たことがあるか
 * firestoreでは、prefixにisを含む場合、JvmFieldをつける
 * */
data class OwnTopic(
    val topicId: Int,
    val title: String,
    @field:JvmField
    val isFavorite: Boolean = false,
    @field:JvmField
    val isRegistered: Boolean = false,
)

fun Topic.toOwnTopic(): OwnTopic =
    OwnTopic(
        topicId = id,
        title = title,
        isFavorite = false,
        isRegistered = false,
    )

val previewOwnTopics = listOf(
    OwnTopic(1, "sample1", isFavorite = true, isRegistered = true),
    OwnTopic(2, "sample2", isFavorite = true, isRegistered = true),
    OwnTopic(3, "sample3", isFavorite = true, isRegistered = true),
    OwnTopic(4, "sample4", isFavorite = true, isRegistered = true),
    OwnTopic(5, "sample5", isFavorite = true, isRegistered = true),
    OwnTopic(6, "sample6", isFavorite = false, isRegistered = false),
    OwnTopic(7, "sample7", isFavorite = false, isRegistered = false),
    OwnTopic(8, "sample8", isFavorite = false, isRegistered = false),
    OwnTopic(9, "sample9", isFavorite = false, isRegistered = false),
    OwnTopic(10, "sample10", isFavorite = false, isRegistered = false),
)
