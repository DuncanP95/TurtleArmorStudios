package com.example.turtlearmorstudios

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    // Insert a new user into the database. If the user already exists, this will replace the old user data.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    // Query to find a user by username and password. Useful for login.
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?

    // Query to check if a username exists in the database. Useful for checking before user registration.
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun findUserByUsername(username: String): User?

    // Optionally, a method to update user data if you plan to allow users to modify their profile.
    @Update
    suspend fun updateUser(user: User): Int

    // Method to delete a user. Could be useful in certain applications.
    @Delete
    suspend fun deleteUser(user: User): Int
}