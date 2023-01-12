package me.jackoshea.whattowatch

/**
 * This is the main activity of my application and represents the entry point to the application with an onCreate method which initialises the database,
 * and MVVM framework
 * @author Jack 0'Shea
 */
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.jackoshea.whattowatch.components.BottomNavBar
import me.jackoshea.whattowatch.data.AppDatabase
import me.jackoshea.whattowatch.data.toMovie
import me.jackoshea.whattowatch.model.AppUiState
import me.jackoshea.whattowatch.model.MovieViewModel
import me.jackoshea.whattowatch.screens.*
import me.jackoshea.whattowatch.ui.theme.WhatToWatchTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Call to ComponentActivity to start the instance.
        super.onCreate(savedInstanceState)
        setContent {
            // Initialise the ROOM Database to store the local movies.
            AppDatabase.getInstance(context = applicationContext)

            // Initialise the View Model which is used to pass events up which trigger UI changes.
            val movieViewModel = MovieViewModel(application)

            // The UI state which is used to get content for all the screens.
            val movieUiState by movieViewModel.uiState.collectAsState()

            // The Navigation Controller is used to navigate around the application through the use of fragments.
            val navController = rememberNavController()

            // The theme which will be used by the application allowing light and dark mode.
            WhatToWatchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    // Using the created components the application is then rendered.
                    MovieApp(navController, movieUiState, movieViewModel)

                }
            }
        }
    }
}

/**
 * The MovieApp which is the main starting point of the application.
 * @param navController: A navigation controller used to navigate through fragments.
 * @param movieUiState: The current state of the UI which refreshes upon changes to the model.
 * @param movieViewModel: The view model which allows passing events to trigger UI updates.
 *
 * @return Unit: Returns nothing as it is just a application UI Component.
 */
@Composable
fun MovieApp(navController: NavHostController, movieUiState: AppUiState, movieViewModel: MovieViewModel) {
    // Sets the current location of the app.
    var location by rememberSaveable { mutableStateOf("popular") }

    /**
     * navigate function which movies the user to a different location in the application.
     * @param value: A string which represents the location the user wishes to go to.
     */
    fun navigate(value: String) {
        location = value
        navController.navigate(location)
    }

    // The scaffold is a compose component allowing for a navbar and top bar component.
    Scaffold(topBar = { me.jackoshea.whattowatch.components.TopAppBar()}, bottomBar = { BottomNavBar(location, onNavigate ={
        navigate(it)
    })}) { padding ->
        // The "nav-host" is responsible for the routing table and moving the user in the application.
        // Each route takes a component as an argument and a route which represents the location.
        NavHost(navController = navController, startDestination = "popular", modifier = Modifier.padding(padding)) {
            composable("popular") { DisplayMovieList(movies = movieUiState.movieState, movieViewModel = movieViewModel, navigate = {navigate("movie")})}
            composable("search") { SearchScreen(movieViewModel = movieViewModel, modifier = Modifier, navigate = {navigate("searched-movies")})}
            composable("movie") { MovieScreen(currentMovie = movieUiState.currentMovie, bookmarked = movieUiState.bookmarked,liked = movieUiState.liked, movieViewModel = movieViewModel)}
            composable("searched-movies") { DisplayMovieList(movies = movieUiState.searchedMovieState, movieViewModel = movieViewModel, navigate = {navigate("movie")}, search = true) }
            composable("liked-movies") {
                // Call to the view model to ensure the room is displaying up to date data.
                movieViewModel.setLikedMovies()
                LikedMovies(movieUiState.likedMovies.map { it.toMovie() }, movieViewModel, navigate = {navigate("movie")})
            }
            composable("bookmarked-movies") {
                // Call to the view model to ensure the room is displaying up to date data.
                movieViewModel.setBookmarkedMovies()
                BookmarkedMovies(movieUiState.bookmarkedMovies.map { it.toMovie() }, movieViewModel, navigate = {navigate("movie")})
            }
        }
    }
}