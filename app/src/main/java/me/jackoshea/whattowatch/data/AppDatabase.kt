package me.jackoshea.whattowatch.data
/**
 * This is my instance of a room database in Kotlin. It follows similar setup to Java but uses a companion object to ensure the database is thread safe
 * and will not have multiple instances.
 * @author Jack O'Shea
 */
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * AppDatabase class which represents my Movie Entity in the program.
 */
@Database(entities = [MovieEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDAO(): MovieDao

    /**
     * Companion object is similar to a singleton which allows only one occurrence.
     * @return AppDatabase?
     */
    companion object {
        //Initialise one instance of the database if one doesn't already exist, else return the current instance.
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                return Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "movie.db"
                ).fallbackToDestructiveMigration().build() //Deletes the database on newer versions which doesn't store any date between migrations.
            }}

    }
}