package com.kunalchhabra.today.repository

import com.kunalchhabra.today.data.TodoDao
import com.kunalchhabra.today.model.TodoEntity
import java.util.*
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoDao: TodoDao){

    suspend fun addTodo(todoEntity: TodoEntity) = todoDao.insert(todoEntity)
    suspend fun getAllTodos() = todoDao.getAll()
    suspend fun getTodoById(id: Int) = todoDao.getById(id)
    suspend fun getTodoByIsDone(isDone: Boolean) = todoDao.getByIsDone(isDone)
    suspend fun getTodoByTitle(title: String) = todoDao.getByTitle(title)
    suspend fun updateTodo(todoEntity: TodoEntity) = todoDao.update(todoEntity)
    suspend fun deleteTodoById(id: UUID) = todoDao.deleteById(id)
    suspend fun deleteTodoByTitle(title: String) = todoDao.deleteByTitle(title)
    suspend fun deleteTodoByIsDone(isDone: Boolean) = todoDao.deleteByIsDone(isDone)
    suspend fun deleteAllTodos() = todoDao.deleteAll()
    suspend fun getTodoCount() = todoDao.getCount()
}