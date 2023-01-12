package me.jackoshea.whattowatch.model
/**
 * The Movie data data class which is used to store the api response and convert it to a movie entity.
 */
import android.util.Log
import me.jackoshea.whattowatch.data.MovieEntity
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Kotlin representation of the API response returned from Movie DB
 * @property page The page of the api response, which could be used to load in multiple sets of responses.
 * @property results The array of movie objects which is returned from retrofit inside the response.
 */
data class ApiResponse(
    val page: Int,
    val results: Array<Movie>,
)

/**
 * Movie data class which stores the data from a movie stored in the api response.
 * @property id The ID assigned by movie DB for the movie.
 * @property backdrop_path The path to get the background path image.
 * @property title The title of the movie.
 * @property overview The description of the movie.
 * @property poster_path The path to get the poster image.
 * @property genre_ids All the ids for the genres.
 * @property popularity The popularity score of the movie.
 * @property release_date When the movie released.
 * @property vote_average The average vote of the movie.
 */
data class Movie(
    val id: Int,
    val backdrop_path: String?,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val genre_ids: Array<Int>,
    val popularity: Double,
    val release_date: String,
    val vote_average: Double,
)

/**
 * Function which converts a Movie to a Movie Entity for storage in the database.
 * @return MovieEntity.
 */
fun Movie.toMovieEntity(liked: Boolean = false, bookmarked: Boolean = false ) = MovieEntity (
    id = id,
    backdrop_path = backdrop_path?: "",
    title = title,
    overview = overview,
    poster_path = poster_path?: "",
    genre_ids = genre_ids.joinToString(separator = ","),
    liked = liked,
    bookmarked = bookmarked,
    popularity = popularity,
    release_date = release_date,
    user_score = 0,
    vote_average = vote_average
)



// The genres returned from the API are integers so i've implemented a map to get the actual genre value.
val genre_map = mapOf(
    28 to "Action",
    12 to "Adventure",
    16 to "Animation",
    35 to "Comedy",
    80 to "Crime",
    99 to "Documentary",
    18 to "Drama",
    10751 to "Family",
    14 to "Fantasy",
    36 to "History",
    27 to "Horror",
    10402 to "Music",
    9648 to "Mystery",
    10749 to "Romance",
    878 to "Science Fiction",
    10770 to "TV Movie",
    53 to "Thriller",
    10752 to "War",
    37 to "Western"
)

// Functional test to ensure data processing is correct.
fun conversionTest() {

    val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .create()
    val m1 = ApiResponse(page = 1, results = arrayOf(Movie(
        backdrop_path = "/bQXAqRx2Fgc46uCVWgoPz5L5Dtr.jpg",
        id = 1,
        title = "Black Panther",
        overview = "About a panther",
        poster_path = "Here",
        genre_ids = arrayOf(1,2,3),
        popularity = 12345.01,
        release_date = "Released",
        vote_average = 7.83,
    )))



    val jsonString: String = gson.toJson(m1)
    Log.d("DEBUG_INFO_HERE",jsonString)

    val json_to_parse= """{
                                                                                                      "page": 1,
                                                                                                      "results": [
                                                                                                        {
                                                                                                          "adult": false,
                                                                                                          "backdrop_path": "/bQXAqRx2Fgc46uCVWgoPz5L5Dtr.jpg",
                                                                                                          "genre_ids": [
                                                                                                            1,
                                                                                                            2,
                                                                                                            3
                                                                                                          ],
                                                                                                          "id": 1,
                                                                                                          "media_type": "movie",
                                                                                                          "original_language": "en",
                                                                                                          "original_title": "Black Adam",
                                                                                                          "overview": "About a panther",
                                                                                                          "popularity": 12345.01,
                                                                                                          "poster_path": "Here",
                                                                                                          "release_data": "Released",
                                                                                                          "title": "Black Panther",
                                                                                                          "video": false,
                                                                                                          "vote_average": 7.83,
                                                                                                          "vote_count": 2156
                                                                                                        }
                                                                                                      ]
                                                                                                    }"""
    val apiRes = gson.fromJson(json_to_parse, ApiResponse::class.java)
    Log.d("DEBUG_INFO_HERE",apiRes.results[0].title)
}