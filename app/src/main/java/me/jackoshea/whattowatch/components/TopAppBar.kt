package me.jackoshea.whattowatch.components
/**
 * The topbar shown on the top of the screen.
 */
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Composable shown at the top of the screen.
 * @param modifier Optional modifier to customise the style or positioning.
 */
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.secondary), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "What to Watch ?",
            style = MaterialTheme.typography.h1,
            modifier = modifier.offset(x = 5.dp, y = 4.dp),
            color = MaterialTheme.colors.onSecondary
        )
    }
}