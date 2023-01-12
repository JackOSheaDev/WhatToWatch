package me.jackoshea.whattowatch.screens
/**
 * Shows the bookmarked movies to the user as a list.
 * @author Jack O'Shea
 */
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.jackoshea.whattowatch.components.MovieCard
import me.jackoshea.whattowatch.model.Movie
import me.jackoshea.whattowatch.model.MovieViewModel

/**
 * BookmarkedMovies shows a list of movies on the screen.
 * @param movies The list of movies you wish to display.
 * @param movieViewModel The viewmodel to call functions.
 * @param navigate A functional callback to navigate to the next screen. Uses hoisting.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookmarkedMovies(movies: List<Movie>, movieViewModel: MovieViewModel, navigate: () -> Unit) {

    // If the movies are not empty show all the movies.
    if(movies.isNotEmpty())
    {
        LazyColumn() {
            items(movies) {
                MovieCard(movie = it, movieViewModel = movieViewModel, navigate = navigate)

            }
        }
    }
    // Otheriwse show text that you have not bookmarked any movies.
    else{
        Box(
            contentAlignment = Alignment.Center, // you apply alignment to all children
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.offset(y = -(15.dp)))
            {
                Text(text = "You have not bookmarked any movies !", textAlign = TextAlign.Center, style= MaterialTheme.typography.h2, color = MaterialTheme.colors.onSecondary)
            }
        }
    }
}