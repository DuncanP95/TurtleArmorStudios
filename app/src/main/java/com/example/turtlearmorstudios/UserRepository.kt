package com.example.turtlearmorstudios

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest

class UserRepository(private val userDao: UserDao) {
    suspend fun authenticate(username: String, password: String): User? {
        return withContext(Dispatchers.IO) {
            val hashedPassword = hashPassword(password)
            userDao.getUserByUsernameAndPassword(username, hashedPassword)
        }
    }

    suspend fun addUser(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val hashedPassword = hashPassword(password)
                val newUser = User(username = username, password = hashedPassword)
                userDao.insertUser(newUser)
                true
            } catch (e: Exception) {
                false
            }
        }
    }


    // Helper function to hash passwords
    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
}