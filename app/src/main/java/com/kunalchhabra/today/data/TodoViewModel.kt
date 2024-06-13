package com.kunalchhabra.today.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunalchhabra.today.model.TodoEntity
import com.kunalchhabra.today.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {
    private val _todos = MutableStateFlow<List<TodoEntity>>(emptyList())
    val todos: StateFlow<List<TodoEntity>> = _todos.asStateFlow()

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            _todos.value = repository.getAllTodos()
        }
    }

    fun addTodo(todoEntity: TodoEntity) {
        viewModelScope.launch {
            repository.addTodo(todoEntity)
            fetchTodos()  // Update the list after adding a new todo
        }
    }

    fun deleteDoneTodos() {
        viewModelScope.launch {
            repository.deleteTodoByIsDone(true)
            fetchTodos()  // Update the list after deleting done todos
        }
    }

    fun deleteTodo(todoEntity: TodoEntity) {
        viewModelScope.launch {
            repository.deleteTodoById(todoEntity.id)
            fetchTodos()  // Update the list after deleting a todo
        }
    }

    fun markAsDone(todoEntity: TodoEntity) {
        viewModelScope.launch {
            repository.updateTodo(todoEntity)
            fetchTodos()  // Update the list after marking as done
        }
    }

    fun updateTodoTitle(todoEntity: TodoEntity, newTitle: String) {
        viewModelScope.launch {
            repository.updateTodo(todoEntity.copy(title = newTitle))
            fetchTodos()  // Update the list after updating the title
        }
    }
}
