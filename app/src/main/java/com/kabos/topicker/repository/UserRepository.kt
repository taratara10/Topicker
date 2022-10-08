package com.kabos.topicker.repository

import com.kabos.model.User

interface UserRepository {

    suspend fun createUser()

    suspend fun getUser(): User
}
