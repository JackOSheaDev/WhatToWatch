package me.jackoshea.whattowatch.components

/**
 * The loading screen shown when the program is awaiting data from the network.
 * @author Jack O'Shea
 */
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * LoadingScreen composable which shows an indeterminate Circular Progress Indicator.
 */
@Composable
fun LoadingScreen()
{
    Box(
        contentAlignment = Alignment.Center, // you apply alignment to all children
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(Modifier.offset(y = -(15.dp)))
    }

}