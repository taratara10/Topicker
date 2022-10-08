package com.kabos.data.repository_impl

import com.kabos.domain.repository.UserRepository
import com.kabos.model.User
import java.util.*

class UserRepositoryImpl(
): UserRepository {

    override suspend fun createUser() {
    }

    override suspend fun getUser(): User {
        return User(uuid = UUID.randomUUID().toString())
    }
}
