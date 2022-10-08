package com.kabos.model

import java.util.*

data class User(
    val uuid: String = UUID.randomUUID().toString(),

    // TODO 運用方法きめる
    val favorite: Int = 0
)
