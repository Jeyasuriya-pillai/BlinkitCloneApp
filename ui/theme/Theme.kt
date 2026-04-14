package com.example.blinkit.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = BlinkitGreen,
    secondary = BlinkitYellow,
    background = BlinkitGray,
    surface = BlinkitWhite,
)

@Composable
fun BlinkitTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}