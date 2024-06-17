package com.kunalchhabra.today.screen

import android.content.Context
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.focus.focusRequester
import android.widget.Toast
import androidx.compose.animation.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
    val context = LocalContext.current
    val newTodoValue = remember { mutableStateOf("") }

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
        AddTodoDialog(showAddTodo, newTodoValue, focusRequester, todoViewModel)
        TitleText()
        Spacer(modifier = Modifier.weight(1f))
        ActionIcons(
            themeColor,
            onThemeColorChange,
            isDarkTheme,
            onDarkThemeChange,
            dynamicColor,
            onDynamicColorChange,
            onSaveThemePreferences,
            todoViewModel,
            context
        )
    }
}

@Composable
fun AddTodoDialog(
    showAddTodo: MutableState<Boolean>,
    newTodoValue: MutableState<String>,
    focusRequester: FocusRequester,
    todoViewModel: TodoViewModel
) {
    val context = LocalContext.current

    AnimatedVisibility(
        showAddTodo.value,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
    ){
        Dialog(
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            onDismissRequest = {
                showAddTodo.value = false
                newTodoValue.value = ""
            }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Add Todo",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = newTodoValue.value,
                        onValueChange = { newTodoValue.value = it },
                        label = { Text("Enter task") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
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
                    Spacer(modifier = Modifier.height(8.dp))
                    AddTodoDialogActions(showAddTodo, newTodoValue, todoViewModel)
                }
            }
        }
    }
}

@Composable
fun AddTodoDialogActions(
    showAddTodo: MutableState<Boolean>,
    newTodoValue: MutableState<String>,
    todoViewModel: TodoViewModel
) {
    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = {
            showAddTodo.value = false
            newTodoValue.value = ""}) {
            Text("Cancel")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
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
        ) {
            Text("Add")
        }
    }
}

@Composable
fun TitleText() {
    Text(
        text = "Today",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(16.dp),
        fontFamily = ItimFontFamily,
        fontSize = 48.sp,
    )
}

@Composable
fun ActionIcons(
    themeColor: String,
    onThemeColorChange: (String) -> Unit,
    isDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    dynamicColor: Boolean,
    onDynamicColorChange: (Boolean) -> Unit,
    onSaveThemePreferences: (String, Boolean, Boolean) -> Unit,
    todoViewModel: TodoViewModel,
    context: Context
) {
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
            imageVector = if (isDarkTheme) Icons.Outlined.DarkMode else Icons.Outlined.LightMode,
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
