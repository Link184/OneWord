package com.link184.oneword.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val Windows97Palette = lightColorScheme(
    primary = Silver,
    secondary = Gray,
    background = White,
    surface = White,
    onPrimary = Black,
    onSecondary = Black,
    onBackground = Black,
    onSurface = Black
)

@Composable
fun Compose97Theme(content: @Composable() () -> Unit) {
    MaterialTheme(
        colorScheme = Windows97Palette,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}