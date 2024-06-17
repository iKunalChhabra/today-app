import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.kunalchhabra.today.ui.theme.SurfaceDark
import com.kunalchhabra.today.ui.theme.SurfaceLight

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddTodo(isDarkTheme: Boolean, onClick: () -> Unit = {}) {
    val keyboard = LocalSoftwareKeyboardController.current

    var isClicked by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isClicked) 2f else 1f,
        animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing),
        finishedListener = {
            isClicked = false
            onClick()
            keyboard?.show()
        }
    )

    val offsetX by animateFloatAsState(
        targetValue = if (isClicked) -300f else 0f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val offsetY by animateFloatAsState(
        targetValue = if (isClicked) -500f else 0f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    Button(
        onClick = {
            isClicked = true
        },
        modifier = Modifier
            .padding(24.dp)
            .size(72.dp)
            .scale(scale)
            .translate(offsetX, offsetY),
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

fun Modifier.translate(x: Float, y: Float): Modifier = this.then(
    Modifier.graphicsLayer {
        translationX = x
        translationY = y
    }
)
