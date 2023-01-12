package me.jackoshea.whattowatch.screens

/**
 * Movie Screen which shows one individual movie.
 * @author Jack O'Shea
 */
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.jackoshea.whattowatch.R
import me.jackoshea.whattowatch.model.Movie
import me.jackoshea.whattowatch.model.MovieViewModel

/**
 * Movie Screen which shows a movie and it's description.
 * @param liked Whether the movie is liked
 * @param bookmarked Whether the movie is bookmarked.
 * @param currentMovie The movie to display
 * @param movieViewModel The view model to use functions to update UI.
 */
@Composable
fun MovieScreen(liked: Boolean, bookmarked: Boolean, currentMovie: Movie, movieViewModel: MovieViewModel){

    Card(elevation = 10.dp, backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxHeight()) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            // Shows the image card.
            AsyncImage(modifier = Modifier
                .clip(
                    RoundedCornerShape(15)
                )
                .align(Alignment.CenterHorizontally)
                .height(300.dp)
                , model = "https://image.tmdb.org/t/p/original${currentMovie.poster_path}", contentDescription ="${currentMovie.title} poster")
            // Shows the title of the movie.
            Text(text=currentMovie.title, style = MaterialTheme.typography.h2, color= MaterialTheme.colors.onSecondary)
            Box(modifier = Modifier.fillMaxHeight(0.75f)){
                Text(modifier = Modifier.verticalScroll(rememberScrollState()),text=currentMovie.overview, style = MaterialTheme.typography.body1)
            }


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                // Liked icon shown as full or empty depending on state.
                if(liked)
                    IconButton(onClick = {movieViewModel.unlikeMovie(currentMovie)}) {
                        Icon(painterResource(id = R.drawable.favourite_filled), contentDescription = "Like", tint = MaterialTheme.colors.onSecondary)
                    }
                else
                {
                    IconButton(onClick = {movieViewModel.likeMovie(currentMovie)}) {
                        Icon(painterResource(id = R.drawable.favourite), contentDescription = "Like")
                    }
                }

                // Bookmarked icon shown as full or empty depending on state.
                if(bookmarked)
                {
                    IconButton(onClick = {movieViewModel.unbookmarkMovie(currentMovie)}) {
                        Icon(painterResource(id = R.drawable.bookmark_filled), contentDescription = "Bookmark", tint = MaterialTheme.colors.onSecondary)
                    }
                }
                else
                {
                    IconButton(onClick = {movieViewModel.bookmarkMovie(currentMovie)}) {
                        Icon(painterResource(id = R.drawable.bookmark), contentDescription = "Bookmark")
                    }
                }
            }
        }

    }
}





