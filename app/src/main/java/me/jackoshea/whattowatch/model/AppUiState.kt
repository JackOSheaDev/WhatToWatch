package me.jackoshea.whattowatch.model
/**
 * This file is used to model the state of the application so no UI elements are directly modified.
 * @author Jack O'Shea
 */
import me.jackoshea.whattowatch.data.MovieEntity

// Similar to an enum but allows for data classes which contain data as variants.
sealed interface MovieState {
    // The success variant is used if the api returns a list of movies.
    data class Success(val movieList: List<Movie>): MovieState
    // If the application cannot connect to the API or the internet.
    object Error: MovieState
    // If the movies are loading on a slow internet connection.
    object Loading: MovieState
}

// Data class which stores the state of the UI.
data class AppUiState(
    // The popular movies loaded from the API.
    var movieState: MovieState = MovieState.Loading,
    // The searched movies requested from the user.
    var searchedMovieState: MovieState = MovieState.Loading,
    // The search query requested by the user.
    var searchParam: String = "",
    // Placeholder for the current movie being looked at.
    var currentMovie: Movie = Movie(
        backdrop_path = "",
        id = 1,
        title = "",
        overview = "",
        poster_path = "",
        genre_ids = arrayOf(),
        popularity = 0.0,
        release_date = "",
        vote_average = 0.0,
    ),
    // Bookmarked state for the current movie placeholder.
    var bookmarked: Boolean = false,
    // Liked state for the current movie placeholder.
    var liked: Boolean = false,
    // List of liked movies from the room database.
    var likedMovies: List<MovieEntity> = listOf(),
    // List of bookmarked movies from the room database.
    var bookmarkedMovies: List<MovieEntity> = listOf()
)



