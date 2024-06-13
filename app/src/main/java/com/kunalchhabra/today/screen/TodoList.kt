package com.kunalchhabra.today.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.kunalchhabra.today.data.TodoViewModel

@Composable
fun TodoList(modifier: Modifier = Modifier, todoViewModel: TodoViewModel) {
    val listState = rememberLazyListState()
    val todos by todoViewModel.todos.collectAsState()

    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(todos.size) { index ->
            TodoItem(todos[index], todoViewModel)
        }
    }
}
