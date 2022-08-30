package com.kabos.topicker.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kabos.topicker.model.entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun createUser(user: User)

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): User?

    @Update
    suspend fun updateFavorite(user: User)
}
