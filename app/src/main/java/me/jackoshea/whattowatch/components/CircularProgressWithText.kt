package me.jackoshea.whattowatch.components

/**
 * CircularProgress which has text in the centre to show a value.
 */
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * CircularProgressWithText shows a value in the center corresponding to the movie score.
 * @param color The color of the circle.
 * @param average_vote The value you wish to display as text.
 * @param modifier Optional modifier for styling and positioning.
 */
@Composable
fun CircularProgressWithText(color: Color, average_vote: Double, modifier: Modifier = Modifier)
{
    Column(modifier = modifier){
        CircularProgressIndicator(color = color, progress = (average_vote / 10).toFloat())
        Text(text = average_vote.toString(), Modifier.offset(y = - (31.dp), x= 9.dp), color = MaterialTheme.colors.onSecondary)
    }
}