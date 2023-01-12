package me.jackoshea.whattowatch.screens

/**
 * Displays a list of movies to the user for the popular and search screen.
 * @author Jack O'Shea
 */
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.jackoshea.whattowatch.components.ErrorScreen
import me.jackoshea.whattowatch.components.LoadingScreen
import me.jackoshea.whattowatch.components.MovieCard
import me.jackoshea.whattowatch.model.MovieState
import me.jackoshea.whattowatch.model.MovieViewModel

/**
 * DisplayMovieList shows all the movies to the user a list of cards.
 * @param search Whether the user is searching for movies or viewing popular movies.
 * @param movies A list of movies to show.
 * @param movieViewModel viewModel to call functions.
 * @param navigate A hoisted function to navigate to a different screen.
 * @param modifier Optional modifier to change style.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DisplayMovieList(search :Boolean = false, movies: MovieState, movieViewModel: MovieViewModel, navigate: () -> Unit, modifier: Modifier = Modifier ){
    val refreshing = movies is MovieState.Loading
    // If the user pulls down it refreshes.
    val pullRefreshState = when(search)
    {
        // Sets the action on pullRefresh.
        true -> rememberPullRefreshState(refreshing = refreshing, onRefresh = {movieViewModel.reloadSearchedMovies()})
        false -> rememberPullRefreshState(refreshing = refreshing, onRefresh = {movieViewModel.reloadPopularMovies()})
    }

    when(movies) {
        // If there are movies they are displayed.
        is MovieState.Success -> {
            if (movies.movieList.isNotEmpty()) {
                Box(modifier = Modifier.pullRefresh(pullRefreshState))
                {
                    Spacer(modifier = Modifier.padding(10.dp))
                    LazyColumn(modifier.pullRefresh(pullRefreshState)) {
                        items(movies.movieList) {
                            MovieCard(
                                movie = it,
                                movieViewModel = movieViewModel,
                                navigate = navigate
                            )

                        }

                    }
                    PullRefreshIndicator(
                        false,
                        pullRefreshState,
                        Modifier.align(Alignment.TopCenter)
                    )
                }

            } else {
                Box(
                    contentAlignment = Alignment.Center, // you apply alignment to all children
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(modifier = Modifier.offset(y = -(15.dp)))
                    {
                        Text(text = "No movies with that name !", style= MaterialTheme.typography.h2, color = MaterialTheme.colors.onSecondary)

                    }
                }
            }
        }
        // If the state is loading it displays the loading screen.
        is MovieState.Loading -> { LoadingScreen() }

        // If there is no network connection.
        is MovieState.Error -> {

                if(search)
                {
                    // Shows an error screen with a function to relaod the searched movies.
                    ErrorScreen{ movieViewModel.reloadSearchedMovies() }
                }
                else
                {
                    // Shows an error screen with a function to relaod the popular movies.
                    ErrorScreen { movieViewModel.reloadPopularMovies()
                }
            }
        }
    }
}