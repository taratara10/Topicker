package com.kabos.model

/**
 * Userが収集したお気に入り等の情報を保持したtopic
 *
 * @param topicId [Topic]を参照するid
 * @param isFavorite お気に入りに指定しているか
 * */
data class OwnTopic(
    val topicId: Int,
    val isFavorite: Boolean,
) 
