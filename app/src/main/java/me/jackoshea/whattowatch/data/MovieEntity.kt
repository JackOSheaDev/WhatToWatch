package me.jackoshea.whattowatch.data
/**
 * MovieEntity is used to represent the rows in a database. It is a 1 to 1 recreation of Movie but without the use of arrays.
 * @author Jack O'Shea
 */
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.jackoshea.whattowatch.model.Movie


/**
 * MovieEntity is the row stored in the database.
 * @property id The ID assigned by movie DB for the movie.
 * @property backdrop_path The path to get the background path image.
 * @property title The title of the movie.
 * @property overview The description of the movie.
 * @property poster_path The path to get the poster image.
 * @property genre_ids All the ids for the genres.
 * @property popularity The popularity score of the movie.
 * @property release_date When the movie released.
 * @property vote_average The average vote of the movie.
 * @property liked The liked status of the movie.
 * @property bookmarked The bookmarked status of the movie.
 * @property user_score The score assigned by the user to the movie (unused)
 */
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name="backdrop_path")
    val backdrop_path: String,
    @ColumnInfo(name="title")
    val title: String,
    @ColumnInfo(name="overview")
    val overview: String,
    @ColumnInfo(name="poster_path")
    val poster_path: String,
    @ColumnInfo(name="genre_ids")
    val genre_ids: String,
    @ColumnInfo(name="popularity")
    val popularity: Double,
    @ColumnInfo(name="release_date")
    val release_date: String,
    @ColumnInfo(name="vote_average")
    val vote_average: Double,

    //Newer variables not stored in movies.
    @ColumnInfo(name="liked")
    val liked: Boolean,
    @ColumnInfo(name="bookmarked")
    val bookmarked: Boolean,
    @ColumnInfo(name="user_score")
    val user_score: Int
)

/**
 * toMovie method which allows conversion from MovieEntity to Movie.
 * @return Movie
 */
fun MovieEntity.toMovie() = Movie(
    id = id,
    backdrop_path = backdrop_path,
    title = title,
    overview = overview,
    poster_path = poster_path,
    genre_ids = genre_ids.split(",").map {it.toInt()}.toTypedArray(),
    popularity = popularity,
    release_date = release_date,
    vote_average = vote_average
)

