package me.jackoshea.whattowatch.data
/**
 * Room interface DAO which is used to interact with the room database. It has several methods listed below.
 * The functions are all suspend coroutines which allows them to be called from other threads and allows asynchronous fetching in the background.
 */
import androidx.room.*

/**
 * Interface which is implemented by the App Database to allow easier queries and additions to the database.
 */
@Dao
interface MovieDao {
    /**
     * getAll function is used to return all the movies in the Room database.
     * @return List<MovieEntity>
     */
    @Query("Select * from movies order by title")
    suspend fun getAll(): List<MovieEntity>

    /**
     * getAllLiked function is used to return all the movies in the Room database that are liked.
     * @return List<MovieEntity>
     */
    @Query("Select * from movies where liked order by title")
    suspend fun getAllLiked(): List<MovieEntity>

    /**
     * getAllBookmarked function is used to return all the movies in the Room database that are bookmarked.
     * @return List<MovieEntity>
     */
    @Query("Select * from movies where bookmarked order by title")
    suspend fun getAllBookmarked(): List<MovieEntity>

    /**
     * getOne function is used to return one movie from the room database.
     * @return MovieEntity
     */
    @Query("Select * from movies where id = :id")
    suspend fun getOne(id: Int): MovieEntity

    /**
     * insert function adds a movie entity to the database.
     * @param movie The MovieEntity you wish to add to the database.
     */
    @Insert
    suspend fun insert(movie: MovieEntity)

    /**
     * delete function adds a movie entity to the database.
     * @param movie The MovieEntity you wish to delete from the database.
     */
    @Delete
    suspend fun delete(movie: MovieEntity)

    /**
     * movieExists function checks if a movie exists in the database.
     * @param id The MovieEntity id you wish to check for.
     * @return Boolean
     */
    @Query("select exists(select * from movies where id = :id)")
    suspend fun movieExists(id: Int): Boolean

    /**
     * movieBookmarked function checks if a movie is bookmarked in the database.
     * @param id The MovieEntity id you wish to check for.
     * @return Boolean
     */
    @Query("select exists(select * from movies where id = :id and bookmarked)")
    suspend fun movieBookmarked(id: Int): Boolean

    /**
     * movieliked function checks if a movie is liked in the database.
     * @param id The MovieEntity id you wish to check for.
     * @return Boolean
     */
    @Query("select exists(select * from movies where id = :id and liked)")
    suspend fun movieLiked(id: Int): Boolean

    /**
     * updateMovie function updates a value in the database..
     * @param movie The MovieEntity you wish to add .
     * @return Boolean
     */
    @Update
    suspend fun updateMovie(movie: MovieEntity)

}

