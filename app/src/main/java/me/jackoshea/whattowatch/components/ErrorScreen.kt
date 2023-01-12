package me.jackoshea.whattowatch.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * ErrorScreen which is used to reload the screen when there is no internet.
 * @param reload A reload function called when the button is clicked.
 */
@Composable
fun ErrorScreen(reload: () -> Unit)
{
    Box(
        contentAlignment = Alignment.Center, // you apply alignment to all children
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.offset(y = -(15.dp)))
        {
            Text(text = "API could not be accessed", style= MaterialTheme.typography.h2, color = MaterialTheme.colors.onSecondary)

            // Button which reloads data from the API when clicked.
            Button(onClick = {reload()}, Modifier.align(Alignment.CenterHorizontally)) {
                Text(text="Reload")
            }
        }
    }
}