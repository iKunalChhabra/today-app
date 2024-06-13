package com.kunalchhabra.today.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kunalchhabra.today.ui.theme.ItimFontFamily
import com.kunalchhabra.today.ui.theme.SurfaceDark

@Composable
fun ScrollableText(text: String, isDone: Boolean, onClick: () -> Unit = {}) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .horizontalScroll(scrollState)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .clickable { onClick() },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = ItimFontFamily,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = SurfaceDark,
                textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None
            )
        )
    }
}
