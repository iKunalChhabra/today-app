package com.kunalchhabra.today.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kunalchhabra.today.ui.theme.ItimFontFamily
import com.kunalchhabra.today.ui.theme.SurfaceDark
import kotlin.random.Random

@Composable
fun ScrollableText(text: String, isDone: Boolean, onClick: () -> Unit = {}) {
    var isClicked by remember { mutableStateOf(false) }
    val maxLines by animateIntAsState(
        targetValue = if (isClicked) 10 else 1, label = "maxLines",
        animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing),
    )
    val color by animateColorAsState(
        targetValue = if (isDone) Color.Gray else SurfaceDark,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    var lineWidth by remember { mutableFloatStateOf(0f) }
    val targetLineWidth by animateFloatAsState(
        targetValue = if (isDone) 1f else 0f,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing), label = ""
    )

    LaunchedEffect(targetLineWidth) {
        lineWidth = targetLineWidth
    }

    Box {
        Text(
            text = text,
            modifier = Modifier
                .clickable {
                    if (isClicked) {
                        onClick()
                    }
                    isClicked = !isClicked
                },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = ItimFontFamily,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = color
            ),
            maxLines = maxLines,
        )
        Canvas(modifier = Modifier.matchParentSize()) {
            val textWidth = size.width
            val y = size.height / 2

            val path = Path().apply {
                moveTo(0f, y)
                var x = 0f
                while (x < lineWidth * textWidth) {
                    quadraticBezierTo(
                        x1 = x + 10f,
                        y1 = y - 10f,
                        x2 = x + 20f,
                        y2 = y
                    )
                    quadraticBezierTo(
                        x1 = x + 30f,
                        y1 = y + 10f,
                        x2 = x + 40f,
                        y2 = y
                    )
                    x += 40f
                }
            }

            drawPath(
                path = path,
                color = color,
                style = Stroke(width = 8f, cap = StrokeCap.Round)
            )
        }
    }
}
