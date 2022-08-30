package com.kabos.topicker.repository

import com.kabos.topicker.model.dao.UserDao
import com.kabos.topicker.model.entity.User
import java.util.*

class UserRepositoryImpl(
    private val userDao: UserDao
): UserRepository {

    override suspend fun createUser() {
        userDao.createUser(User(uuid = UUID.randomUUID().toString()))
    }

    override suspend fun getUser(): User {
        return userDao.getUser()!!
    }
}
