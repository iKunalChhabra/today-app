package com.kunalchhabra.today.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kunalchhabra.today.data.TodoViewModel
import com.kunalchhabra.today.data.motivationalToast
import com.kunalchhabra.today.model.TodoEntity
import com.kunalchhabra.today.ui.theme.SurfaceDark
import com.kunalchhabra.today.ui.theme.THEME_COLOR_CARD
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItem(todo: TodoEntity, todoViewModel: TodoViewModel) {
    var isDone by remember { mutableStateOf(todo.isDone) }
    var isEditing by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(todo.title)) }
    val context = LocalContext.current

    SwipeToDismiss(
        state = rememberDismissState(
            confirmValueChange = {
                if (it == DismissValue.DismissedToStart) {
                    todoViewModel.deleteTodo(todo)
                    Toast.makeText(context, "Task deleted!", Toast.LENGTH_SHORT).show()
                    true
                } else {
                    false
                }
            }
        ),
        directions = setOf(DismissDirection.EndToStart),
        background = { SwipeToDeleteBackground() },
        dismissContent = {
            TodoCard(
                todo = todo,
                isDone = isDone,
                isEditing = isEditing,
                textFieldValue = textFieldValue,
                onIconClick = {
                    isDone = !isDone
                    todoViewModel.markAsDone(todo.copy(isDone = isDone))
                    if (isDone) Toast.makeText(context, motivationalToast(), Toast.LENGTH_SHORT).show()
                },
                onTextClick = { isEditing = true },
                onTextChange = { textFieldValue = it },
                onDoneAction = {
                    isEditing = false
                    todoViewModel.updateTodoTitle(
                        todo, textFieldValue.text
                    )
                    Toast.makeText(context, "Task updated!", Toast.LENGTH_SHORT).show()
                }
            )
        }
    )
}

@Composable
fun SwipeToDeleteBackground() {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color(0xFFFF6961), shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Outlined.Delete,
            contentDescription = "Delete Icon",
            tint = Color.White,
            modifier = Modifier
                .size(48.dp)
                .padding(2.dp)
        )
    }
}

@Composable
fun TodoCard(
    todo: TodoEntity,
    isDone: Boolean,
    isEditing: Boolean,
    textFieldValue: TextFieldValue,
    onIconClick: () -> Unit,
    onTextClick: () -> Unit,
    onTextChange: (TextFieldValue) -> Unit,
    onDoneAction: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = THEME_COLOR_CARD,
        ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TodoIcon(isDone = isDone, onClick = onIconClick)
            TodoText(
                todo = todo,
                text = textFieldValue,
                isEditing = isEditing,
                onTextClick = onTextClick,
                onTextChange = onTextChange,
                onDoneAction = onDoneAction
            )
        }
    }
}

@Composable
fun TodoIcon(isDone: Boolean, onClick: () -> Unit) {
    Icon(
        imageVector = if (isDone) Icons.Outlined.TaskAlt else Icons.Outlined.Circle,
        tint = SurfaceDark,
        contentDescription = "Todo",
        modifier = Modifier
            .size(32.dp)
            .clickable { onClick() }
    )
}

@Composable
fun TodoText(
    todo: TodoEntity,
    text: TextFieldValue,
    isEditing: Boolean,
    onTextClick: () -> Unit,
    onTextChange: (TextFieldValue) -> Unit,
    onDoneAction: () -> Unit
) {
    if (isEditing) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Sentences
            ),
            keyboardActions = KeyboardActions(
                onDone = { onDoneAction() }
            ),
            modifier = Modifier.fillMaxWidth()
                .background(THEME_COLOR_CARD, RoundedCornerShape(16.dp))
        )
    } else {
        ScrollableText(
            text = text.text,
            isDone = todo.isDone,
            onClick = onTextClick
        )
    }
}
