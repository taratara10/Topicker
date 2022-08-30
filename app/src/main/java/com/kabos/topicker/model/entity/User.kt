package com.kabos.topicker.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User(
    @PrimaryKey
    val uuid: String = UUID.randomUUID().toString(),

    // TODO 運用方法きめる
    @ColumnInfo
    val favorite: Int = 0

)
