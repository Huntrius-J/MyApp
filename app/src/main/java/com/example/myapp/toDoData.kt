package com.example.myapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class toDoData(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "completed") var completed: Boolean?
)