package com.kunalchhabra.today

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kunalchhabra.today.data.*
import com.kunalchhabra.today.screen.HomeScreen
import com.kunalchhabra.today.ui.theme.TodayTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeViewModel = viewModel(ThemeViewModel::class.java)
            val themePreferences by themeViewModel.themePreferences.collectAsState()

            if (themePreferences != null) {
                var useBlueTheme by remember { mutableStateOf(themePreferences?.useBlueTheme ?: false) }
                var isDarkTheme by remember { mutableStateOf(themePreferences?.isDarkTheme ?: false) }

                TodayTheme(useBlueTheme = useBlueTheme, darkTheme = isDarkTheme) {
                    Column {
                        TodoApp(
                            useBlueTheme = useBlueTheme,
                            onBlueThemeChange = { useBlueTheme = it },
                            isDarkTheme = isDarkTheme,
                            onDarkThemeChange = { isDarkTheme = it },
                            onSaveThemePreferences = { blueTheme, darkTheme ->
                                themeViewModel.saveThemePreferences(blueTheme, darkTheme)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TodoApp(
    useBlueTheme: Boolean,
    onBlueThemeChange: (Boolean) -> Unit,
    isDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    onSaveThemePreferences: (Boolean, Boolean) -> Unit,
    todoViewModel: TodoViewModel = viewModel(modelClass = TodoViewModel::class)
) {
    HomeScreen(
        todoViewModel = todoViewModel,
        useBlueTheme = useBlueTheme,
        onBlueThemeChange = onBlueThemeChange,
        isDarkTheme = isDarkTheme,
        onDarkThemeChange = onDarkThemeChange,
        onSaveThemePreferences = onSaveThemePreferences
    )
}
