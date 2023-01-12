package me.jackoshea.whattowatch.screens

/**
 * LikedMovies displays all the user's liked movies to them.
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
 * LikedMovies displays all the movies a user liked/favourited as a list of cards.
 * @param movies A list of the movies you wish to show.
 * @param movieViewModel The viewModel to call functions.
 * @param navigate A function to navigate to the next screen.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LikedMovies(movies: List<Movie>, movieViewModel: MovieViewModel, navigate: () -> Unit) {
    if(movies.isNotEmpty())
    {
        // Lazy list to show all the cards as elements.
        LazyColumn() {
            items(movies) {
                MovieCard(movie = it, movieViewModel = movieViewModel, navigate = navigate)

            }
        }
    }
    // If the list is empty show a text instead.
    else{
        Box(
            contentAlignment = Alignment.Center, // you apply alignment to all children
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.offset(y = -(15.dp)))
            {
                Text(text = "You have not liked any movies !", textAlign = TextAlign.Center, style= MaterialTheme.typography.h2, color = MaterialTheme.colors.onSecondary)
            }
        }
    }
}