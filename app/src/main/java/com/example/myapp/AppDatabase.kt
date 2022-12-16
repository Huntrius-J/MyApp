package com.example.myapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [toDoData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoDao(): toDoDao

    companion object{
        fun getDatabase(context: Context):AppDatabase
        {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "Article"
            ).allowMainThreadQueries().build()
        }
    }
}