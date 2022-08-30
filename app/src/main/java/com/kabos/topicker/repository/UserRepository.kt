package com.kabos.topicker.repository

import com.kabos.topicker.model.entity.User

interface UserRepository {

    suspend fun createUser()

    suspend fun getUser(): User
}
