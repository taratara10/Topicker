package com.kabos.topicker.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kabos.topicker.model.dao.UserDao
import com.kabos.topicker.model.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}
