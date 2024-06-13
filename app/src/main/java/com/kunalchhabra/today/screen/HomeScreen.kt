import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kunalchhabra.today.data.TodoViewModel
import com.kunalchhabra.today.data.addTodoToast
import com.kunalchhabra.today.data.motivationalToast
import com.kunalchhabra.today.model.TodoEntity
import com.kunalchhabra.today.ui.theme.*
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class)
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
            TodoList(modifier = Modifier.padding(innerPadding), todoViewModel = todoViewModel)
        }
    }
}

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
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (showAddTodo.value) {
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
        } else {
            Text(
                text = "Today",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp),
                fontFamily = ItimFontFamily,
                fontSize = 48.sp,
            )
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
                            // Toggle between theme colors
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
                            Toast.makeText(context, "All completed tasks deleted!", Toast.LENGTH_SHORT).show()
                        }
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddTodo(isDarkTheme: Boolean, onClick: () -> Unit = {}) {
    val keyboard = LocalSoftwareKeyboardController.current
    Button(
        onClick = {
            onClick()
            keyboard?.show()
        },
        modifier = Modifier
            .padding(24.dp)
            .size(72.dp),
        shape = CircleShape,
        elevation = ButtonDefaults.buttonElevation(10.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.AddCircle,
                contentDescription = "Add Todo",
                modifier = Modifier.size(72.dp),
                tint = if (isDarkTheme) SurfaceDark else SurfaceLight
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItem(todo: TodoEntity, todoViewModel: TodoViewModel) {
    var isDone by remember { mutableStateOf(todo.isDone) }
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
        background = {
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxHeight()
                    .fillMaxWidth().background(color = Color(0xFFFF6961), shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete Icon",
                    tint = THEME_COLOR_CARD,
                    modifier = Modifier.size(48.dp).padding(2.dp)
                )
            }
        },
        dismissContent = {
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
                    Icon(
                        imageVector = if (isDone) Icons.Outlined.TaskAlt else Icons.Outlined.Circle,
                        tint = SurfaceDark,
                        contentDescription = "Todo",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                isDone = !isDone
                                todoViewModel.markAsDone(todo.copy(isDone = isDone))
                                if (isDone) Toast.makeText(context, motivationalToast(), Toast.LENGTH_SHORT).show()
                            }
                    )
                    ScrollableText(text = todo.title, isDone = isDone)
                }
            }
        }
    )
}

@Composable
fun ScrollableText(text: String, isDone: Boolean) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .horizontalScroll(scrollState)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = ItimFontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = SurfaceDark,
                textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None
            )
        )
    }
}

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
