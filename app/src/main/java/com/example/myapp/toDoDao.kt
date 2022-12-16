package com.example.myapp

import androidx.room.*


@Dao
interface toDoDao {
    @Query("SELECT * FROM toDoData")
    fun getAll(): List<toDoData>

    @Insert
    fun insertAll(vararg todolist: toDoData)

    @Update
    fun update(todo: toDoData)

    @Delete
    fun delete(todo: toDoData)

    @Query("DELETE FROM toDoData")
    fun clearTable()
}