package me.jackoshea.whattowatch.components

/**
 * Movie card used on the popular and searched pages.
 * @author Jack O'Shea
 */
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.jackoshea.whattowatch.model.Movie
import me.jackoshea.whattowatch.model.MovieViewModel
import me.jackoshea.whattowatch.model.genre_map


/**
 * MovieCard is a composable which shows a movie and it's associated information.
 * @param movieViewModel The viewModel of the application to call functions upon events.
 * @param movie The movie being shown by the card.
 * @param navigate A function to navigate to another screen
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieCard(movieViewModel: MovieViewModel, movie: Movie, navigate: () -> Unit) {
    Card(elevation = 10.dp, backgroundColor = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.padding(15.dp),
        onClick = {
            movieViewModel.setMovie(movie)
            navigate()

        }) {
        // The color of the progress indicator is decided on based on the vote average score.
        val color = when(movie.vote_average) {
            in 0f..5f -> {
                Color.Red
            }
            in 5f..7f -> {
                Color.Yellow
            }
            else -> {
                Color.Green
            }
        }

        // Column to hold all the elements.
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()) {
            AsyncImage(modifier = Modifier
                .clip(RoundedCornerShape(5))
                .align(Alignment.CenterHorizontally), model = "https://image.tmdb.org/t/p/original${movie.backdrop_path}", contentDescription ="${movie.title} poster")

            // Movie title.
            Text(text=movie.title,style = MaterialTheme.typography.h2, color= MaterialTheme.colors.onSecondary, modifier = Modifier.padding(start=2.dp))


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text="Released: ${movie.release_date}",style = MaterialTheme.typography.h3, modifier =  Modifier.padding(start=2.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)){
                        // If the movie has two or more genres only two are shown.
                        if(movie.genre_ids.size >= 2)
                        {   // Maps each genre to a UI component.
                            movie.genre_ids.slice(0..1).forEach {
                                genre_map[it]?.let { text_value ->
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(color = MaterialTheme.colors.background),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = text_value,
                                            modifier = Modifier.padding(15.dp, 5.dp)
                                        )
                                    }
                                }
                            }
                        }
                        else
                        {
                            movie.genre_ids.forEach {
                                genre_map[it]?.let { text_value ->
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(color = MaterialTheme.colors.background),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = text_value,
                                            modifier = Modifier.padding(15.dp, 5.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Circular progress bar which has a text value for the vote average inside.
                CircularProgressWithText(color = color, average_vote = movie.vote_average, modifier = Modifier.offset(y =  (20).dp))
            }

        }



    }
}