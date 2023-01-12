package me.jackoshea.whattowatch.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * The DarkColorPalette is used for phones/tablets that have a preference for dark mode.
 */
private val DarkColorPalette = darkColors(
    primary = darkPurple,
    primaryVariant = darkPurple,
    secondary = darkPurple,

    //Other default colors to override
    background = darkBlue,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

/**
 * The LightColorPalette is used for phones/tablets that have a preference for light mode.
 */
private val LightColorPalette = lightColors(
    primary = blue,
    primaryVariant = dark_blue,
    secondary = gray,

    //Other default colors to override
    background = dark_gray,
    surface = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,

    /*
    primary = Color(0xffeeeeee),
    primaryVariant = Color(0xffeeeeee),
    secondary = gray,

    //Other default colors to override
    background = dark_gray,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.Black,
     */
)

/**
 * WhatToWatchTheme is the main theme of the application and is used in the MainActivity to set the theme for the whole application.
 * @param darkTheme: A boolean which tells the code whether to return the light or dark mode pallette.
 * @param content: A composable which goes inside the pallette ensuring the theme is set for all the contained UI elements.
 */
@Composable
fun WhatToWatchTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}