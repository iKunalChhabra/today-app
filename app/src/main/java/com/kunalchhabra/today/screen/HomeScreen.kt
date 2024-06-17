package com.kunalchhabra.today.ui.screen

import AddTodo
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kunalchhabra.today.data.TodoViewModel
import com.kunalchhabra.today.screen.TopBar

@Composable
fun HomeScreen(
    todoViewModel: TodoViewModel,
    themeColor: String,
    onThemeColorChange: (String) -> Unit,
    isDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    dynamicColor: Boolean,
    onDynamicColorChange: (Boolean) -> Unit,
    onSaveThemePreferences: (String, Boolean, Boolean) -> Unit
) {
    val addTodoClicked = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Scaffold(
            topBar = {
                TopBar(
                    showAddTodo = addTodoClicked,
                    focusRequester = focusRequester,
                    todoViewModel = todoViewModel,
                    themeColor = themeColor,
                    onThemeColorChange = onThemeColorChange,
                    isDarkTheme = isDarkTheme,
                    onDarkThemeChange = onDarkThemeChange,
                    dynamicColor = dynamicColor,
                    onDynamicColorChange = onDynamicColorChange,
                    onSaveThemePreferences = onSaveThemePreferences
                )
            },
            floatingActionButton = {
                AddTodo(isDarkTheme = isDarkTheme) {
                    addTodoClicked.value = true
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                if (todoViewModel.todos.collectAsState().value.isEmpty()) {
                    Text(
                        text = "You have achieved it all! 🎉",
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    TodoList(modifier = Modifier.fillMaxSize(), todoViewModel = todoViewModel)
                }
            }
        }
    }
}
