package com.kunalchhabra.today.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kunalchhabra.today.model.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao

}