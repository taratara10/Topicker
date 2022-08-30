package com.kabos.topicker.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User(
    @PrimaryKey
    val uuid: String = UUID.randomUUID().toString(),

    @ColumnInfo
    val favorite: Int = 0

)
