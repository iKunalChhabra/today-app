package com.kunalchhabra.today

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kunalchhabra.today.data.*
import com.kunalchhabra.today.ui.screen.HomeScreen
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
                var themeColor by remember { mutableStateOf(themePreferences?.themeColor ?: "Pink") }
                var isDarkTheme by remember { mutableStateOf(themePreferences?.isDarkTheme ?: false) }
                var dynamicColor by remember { mutableStateOf(themePreferences?.dynamicColor ?: false) }

                TodayTheme(
                    themeColor = themeColor,
                    darkTheme = isDarkTheme,
                    dynamicColor = dynamicColor
                ) {
                    Column {
                        TodoApp(
                            themeColor = themeColor,
                            onThemeColorChange = { themeColor = it },
                            isDarkTheme = isDarkTheme,
                            onDarkThemeChange = { isDarkTheme = it },
                            dynamicColor = dynamicColor,
                            onDynamicColorChange = { dynamicColor = it },
                            onSaveThemePreferences = { color, dark, dynamic ->
                                themeViewModel.saveThemePreferences(color, dark, dynamic)
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
    themeColor: String,
    onThemeColorChange: (String) -> Unit,
    isDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    dynamicColor: Boolean,
    onDynamicColorChange: (Boolean) -> Unit,
    onSaveThemePreferences: (String, Boolean, Boolean) -> Unit,
    todoViewModel: TodoViewModel = viewModel(modelClass = TodoViewModel::class)
) {
    HomeScreen(
        todoViewModel = todoViewModel,
        themeColor = themeColor,
        onThemeColorChange = onThemeColorChange,
        isDarkTheme = isDarkTheme,
        onDarkThemeChange = onDarkThemeChange,
        dynamicColor = dynamicColor,
        onDynamicColorChange = onDynamicColorChange,
        onSaveThemePreferences = onSaveThemePreferences
    )
}
