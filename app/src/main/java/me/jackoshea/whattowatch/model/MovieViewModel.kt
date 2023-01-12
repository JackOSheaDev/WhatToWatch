package me.jackoshea.whattowatch.model
/**
 * This is the view model responsible for handling state information,
 * and modifying the UI state.
 */
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import me.jackoshea.whattowatch.data.AppDatabase
import me.jackoshea.whattowatch.network.MovieApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * The MovieViewModel class uses android view model to gain access to the application context.
 * It initialises the database and UI state for the application to use.
 *
 * @param application: The application context to allow the use of the database.
 */
class MovieViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * The _uiState is a private variable which controls the data for the application.
     * Follows the single source of truth principle.
     */
    private var _uiState = MutableStateFlow(AppUiState())

    /**
     * The _db is a reference to the movieDAO allowing updates and changes to the ROOM DB.
     */
    private var _db = AppDatabase.getInstance(application.applicationContext).movieDAO()

    /**
     * The _apiKey is used to connect to Movie DB and get queries.
     */
    private val _apiKey = "" //Must be replaced with api key

    /**
     * The uiState is an immutable version of uiState which is safe for the Main Activity to access.
     */
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    /**
     * Initialiser function to get the movies for popular.
     */
    init {
        getMovies()
    }

    /**
     * getMovies is a function which launches an Async task in the background on a seperate thread
     * to gather movies from the API.
     *
     * @return Unit
     */
    private fun getMovies() {
        // Uses viewModelScope to launch a task on a seperate thread.
        viewModelScope.launch {
            // Copies teh existing value of the _uiState and modifies the state to be loading while
            // data is fetched.
            _uiState.value = _uiState.value.copy(movieState = MovieState.Loading)
            val result = try {
                // Tries to get the data from the API.
                MovieState.Success(MovieApi.retrofitService.getPopularMovies(_apiKey,
                    page=1,
                ).results.toList())
            } catch (e: IOException) {
                // If there is an error the state is set to Error so it can be handeled by the UI.
                MovieState.Error
            } catch (e: HttpException) {
                // If there is an error the state is set to Error so it can be handeled by the UI.
                MovieState.Error
            }
            // Sets state after the values have been gathered.
            _uiState.value = _uiState.value.copy(movieState = result)
        }
    }

    /**
     * setQuery is a function which takes a string as a parameter and returns movies containing that word.
     * @param param A string which is what the user searches for.
     */
    fun setQuery(param: String) {
        // Launches a task on a background thread.
        viewModelScope.launch {
            val result = try {

                MovieState.Success(MovieApi.retrofitService.getSearch(_apiKey,
                    page=1,
                    query = param
                ).results.toList())
            } catch (e: IOException) {
                MovieState.Error
            } catch (e: HttpException) {
                MovieState.Error
            }
            // Sets the result of the searched movies.
            _uiState.value = _uiState.value.copy(searchParam = param, searchedMovieState = result)
        }


    }

    /**
     * When the user chooses a movie, an event is passed to the view model to handle.
     * @param movie The movie the user wishes to view.
     */
    fun setMovie(movie: Movie) {
        viewModelScope.launch {
            try{
                Log.d("JACK", _db.movieBookmarked(movie.id).toString())
            }catch(ex: Exception)
            {
                Log.d("JACK", ex.toString())
            }
            // Sets the current movies to the movie passed from the event.
            _uiState.value = _uiState.value.copy(currentMovie = movie, liked = _db.movieLiked(movie.id), bookmarked =_db.movieBookmarked(movie.id))
        }
    }

    /**
     * This function is used to like a movie the user chooses.
     * @param movie The movie the user wishes to like.
     */
    fun likeMovie(movie: Movie)
    {
        viewModelScope.launch {
            // Checks if the movie is in the room database, if it is updates the value.
            if(_db.movieExists(movie.id))
            {
                // Already exists just needs to be updated.
                _db.updateMovie(_db.getOne(movie.id).copy(liked = true))
            }
            else
            {
                // Otherwise it is inserted into the database.
                _db.insert(movie.toMovieEntity(liked=true))
            }

            // Updates the UI to reflect that the movie's liked status has changed.
            _uiState.value = _uiState.value.copy(currentMovie = movie, liked = _db.movieLiked(movie.id), bookmarked =_db.movieBookmarked(movie.id))

            // Call to setLikedMovies to update the favourited list.
            setLikedMovies()

        }
    }

    /**
     * This function is used to unlike a movie the user chooses.
     * @param movie The movie the user wishes to unlike.
     */
    fun unlikeMovie(movie: Movie)
    {
        viewModelScope.launch {
            try{
                // Updates the movie and sets the liked state to false.
                _db.updateMovie(_db.getOne(movie.id).copy(liked = false))

                // If the movie is not bookmarked it is deleted from the room database to save space.
                if (!_db.getOne(movie.id).bookmarked)
                {
                    _db.delete(movie.toMovieEntity())
                }

            }
            catch(ex: Exception)
            {
                Log.d("JACK",ex.toString())
            }
            // Modifies the UI state to show the value is no longer liked.
            _uiState.value = _uiState.value.copy(currentMovie = movie, liked = _db.movieLiked(movie.id), bookmarked =_db.movieBookmarked(movie.id))
            // Sets the liked movies for the favourited list.
            setLikedMovies()

        }

    }

    /**
     * This function is used to bookmark a movie the user wishes to watch.
     * @param movie The movie the user wishes to bookmark to watch later.
     */
    fun bookmarkMovie(movie: Movie)
    {
        viewModelScope.launch {
            try{
                // If the movie exists updates the bookmarked status
                if(_db.movieExists(movie.id))
                {
                    //Already exists just needs to be updated.
                    _db.updateMovie(_db.getOne(movie.id).copy(bookmarked = true))

                }
                else
                {
                    // Insert the movie into the database with the bookmarked state of true.
                    _db.insert(movie.toMovieEntity(bookmarked =true))
                }
            }
            catch(ex: Exception)
            {
                Log.d("JACK",ex.toString())
            }
            // Updates the bookmarked movies state in the UI state.
            setBookmarkedMovies()
            // Sets the UI element to reflect the change made in the list.
            _uiState.value = _uiState.value.copy(currentMovie = movie, liked = _db.movieLiked(movie.id), bookmarked =_db.movieBookmarked(movie.id))
        }
    }

    /**
     * This function is used to unbookmark a movie the user no longer wishes to watch
     * @param movie The movie the user wishes to remove from the bookmarked list.
     */
    fun unbookmarkMovie(movie: Movie)
    {
        viewModelScope.launch {
            try{
                // Updates the movie to no longer be bookmarked.
                _db.updateMovie(_db.getOne(movie.id).copy(bookmarked = false))

                // If the movie is not liked it can be safely removed from the room database.
                if (!_db.getOne(movie.id).liked)
                {
                    _db.delete(movie.toMovieEntity())
                }

            }
            catch(ex: Exception)
            {
                Log.d("JACK",ex.toString())
            }

            // Updates the list to reflect the change in the bookmarked status.
            setBookmarkedMovies()
            // Updates the UI element on the screen to allow it to be bookmarked again.
            _uiState.value = _uiState.value.copy(currentMovie = movie, liked = _db.movieLiked(movie.id), bookmarked =_db.movieBookmarked(movie.id))
        }

    }

    /**
     * setsLikedMovies sets the liked movies into the state.
     */
    fun setLikedMovies(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(likedMovies = _db.getAllLiked())
        }
    }

    /**
     * setsBookmarkedMovies sets the bookmarked movies into the state.
     */
    fun setBookmarkedMovies(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(bookmarkedMovies = _db.getAllBookmarked())
        }
    }

    /**
     * Reload function to try to get the popular movies again.
     */
    fun reloadPopularMovies()
    {
        getMovies()
    }

    /**
     * Reload function to try to get the searched movies again.
     */
    fun reloadSearchedMovies()
    {
        setQuery(_uiState.value.searchParam)
    }
}



