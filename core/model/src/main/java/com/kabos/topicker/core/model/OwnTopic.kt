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
