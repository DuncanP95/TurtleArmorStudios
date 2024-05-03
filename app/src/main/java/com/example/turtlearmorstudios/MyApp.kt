package com.example.turtlearmorstudios

import android.app.Application
import androidx.room.Room

class MyApp : Application() {
    val database by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "database-name").build()
    }
}