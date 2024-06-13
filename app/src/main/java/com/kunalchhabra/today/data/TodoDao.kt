package com.kunalchhabra.today.data

import androidx.room.*
import com.kunalchhabra.today.model.TodoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todoEntity: TodoEntity)

    @Query("SELECT * FROM todoentity")
    suspend fun getAll(): List<TodoEntity>

    @Query("SELECT * FROM todoentity WHERE id = :id")
    suspend fun getById(id: Int): TodoEntity

    @Query("SELECT * FROM todoentity WHERE isDone = :isDone")
    suspend fun getByIsDone(isDone: Boolean): List<TodoEntity>

    @Query("SELECT * FROM todoentity WHERE title = :title")
    suspend fun getByTitle(title: String): TodoEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(todoEntity: TodoEntity)

    @Query("DELETE FROM todoentity WHERE id = :id")
    suspend fun deleteById(id: java.util.UUID)

    @Query("DELETE FROM todoentity WHERE title = :title")
    suspend fun deleteByTitle(title: String)

    @Query("DELETE FROM todoentity WHERE isDone = :isDone")
    suspend fun deleteByIsDone(isDone: Boolean)

    @Query("DELETE FROM todoentity")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM todoentity")
    suspend fun getCount(): Int
}
