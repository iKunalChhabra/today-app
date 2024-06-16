package com.kunalchhabra.today.screen

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.focus.focusRequester
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kunalchhabra.today.data.TodoViewModel
import com.kunalchhabra.today.data.addTodoToast
import com.kunalchhabra.today.model.TodoEntity
import com.kunalchhabra.today.ui.theme.ItimFontFamily

@Composable
fun TopBar(
    showAddTodo: MutableState<Boolean>,
    focusRequester: FocusRequester,
    todoViewModel: TodoViewModel,
    themeColor: String,
    onThemeColorChange: (String) -> Unit,
    isDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    dynamicColor: Boolean,
    onDynamicColorChange: (Boolean) -> Unit,
    onSaveThemePreferences: (String, Boolean, Boolean) -> Unit
) {
    val newTodoValue = remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(showAddTodo.value) {
        if (showAddTodo.value) {
            focusRequester.requestFocus()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(132.dp)
            .padding(16.dp)
    ) {
        AnimatedVisibility(showAddTodo.value) {
            OutlinedTextField(
                value = newTodoValue.value,
                onValueChange = { newTodoValue.value = it },
                label = { Text("Add Todo") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .focusRequester(focusRequester),
                keyboardActions = KeyboardActions(
                    onDone = {
                        showAddTodo.value = false
                        if (newTodoValue.value.isNotBlank()) {
                            todoViewModel.addTodo(
                                TodoEntity(
                                    title = newTodoValue.value.trim(),
                                    isDone = false
                                )
                            )
                            newTodoValue.value = ""
                            Toast.makeText(context, addTodoToast(), Toast.LENGTH_SHORT).show()
                        }
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
        }
        if(!showAddTodo.value) {
            Text(
                text = "Today",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp),
                fontFamily = ItimFontFamily,
                fontSize = 48.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Palette,
                    contentDescription = "Theme",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            val newThemeColor = when (themeColor) {
                                "Pink" -> "Blue"
                                "Blue" -> "Yellow"
                                "Yellow" -> "Purple"
                                "Purple" -> "Green"
                                "Green" -> "Pink"
                                else -> "Pink"
                            }
                            onThemeColorChange(newThemeColor)
                            onSaveThemePreferences(newThemeColor, isDarkTheme, dynamicColor)
                        }
                )
                Icon(
                    imageVector = Icons.Outlined.DarkMode,
                    contentDescription = "Dark Mode",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onDarkThemeChange(!isDarkTheme)
                            onSaveThemePreferences(themeColor, !isDarkTheme, dynamicColor)
                        }
                )
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    tint = Color(0XFFFF5733),
                    contentDescription = "Todo",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            todoViewModel.deleteDoneTodos()
                            Toast
                                .makeText(context, "All completed tasks deleted!", Toast.LENGTH_SHORT)
                                .show()
                        }
                )
            }
        }
    }
}
