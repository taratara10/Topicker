package com.kabos.domain.repository

import com.kabos.model.User

interface UserRepository {

    suspend fun createUser()

    suspend fun getUser(): User
}
