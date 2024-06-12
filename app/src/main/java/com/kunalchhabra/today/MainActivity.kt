package com.kunalchhabra.today

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kunalchhabra.today.data.TodoViewModel
import com.kunalchhabra.today.screen.HomeScreen
import com.kunalchhabra.today.ui.theme.TodayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodayTheme {
                TodoApp()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    TodayTheme {
        TodoApp()
    }
}

@Composable
fun TodoApp(todoViewModel: TodoViewModel = viewModel(modelClass = TodoViewModel::class)) {
    HomeScreen(todoViewModel = todoViewModel)
}

