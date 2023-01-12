/**
 * This file is used to connect to the Movie DB api. It uses retrofit for the async loading and GSON for the conversion feature.
 * @author Jack O'Shea
 */
package me.jackoshea.whattowatch.network

// Imports Api Response model which is used to model the data returned from the api.
import me.jackoshea.whattowatch.model.ApiResponse

// Retrofit is used to request data from an API and convert it to the correct data class.
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The URL all data used in the project is requested from.
 */
private const val BASE_URL =
    "https://api.themoviedb.org/3/"

/**
 * Retrofit instance is responsible for making requests to the online API.
 */
private val retrofit = Retrofit.Builder()
    // A converter factory is used to format all returned API responses into the correct data type.
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

/**
 * The Movie API interface defines all the operations the retrofit instance can use.
 */
interface MovieApiService {

    /**
     * getPopularMovies is used to return the top 20 popular movies released lately.
     * @param key The API key to request the data from MovieDB
     * @param page The page of results you want as the API returns 20 results at a time.
     *
     * @return ApiResponse
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key: String,
        @Query("page") page: Int
    ): ApiResponse

    /**
     * getSearch is used to return all movies which contain a query word which is sent to the MovieDB Api.
     *
     * @param key The API key to request the data from MovieDB
     * @param page The page of results you want as the API returns 20 results at a time.
     * @param query The query passed to the API that you wish to search for.
     *
     * @return ApiResponse
     */
    @GET("search/movie")
    suspend fun getSearch(
        @Query("api_key") key: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): ApiResponse


}

/**
 * Lazy instantiation of the Movie API object to prevent extra processing.
 * @property retrofitService A retrofit instance which is shared by the application to request new data from the API.
 */
object MovieApi {
    // The "by lazy" extension ensures the retrofitservice is initialised during the first call to prevent extra processing at start up time.
    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}