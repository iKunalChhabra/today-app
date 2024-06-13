package com.kunalchhabra.today.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.kunalchhabra.today.ui.theme.SurfaceDark
import com.kunalchhabra.today.ui.theme.SurfaceLight

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
