package com.example.flightsearch.data.databases.flightsearch

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flightsearch.data.databases.flightsearch.dao.AirportDao
import com.example.flightsearch.data.databases.flightsearch.dao.FavoriteDao
import com.example.flightsearch.data.databases.flightsearch.entities.AirportEntity
import com.example.flightsearch.data.databases.flightsearch.entities.FavoriteEntity

/**
 * The Room database for the Flight Search application.
 *
 * This database stores information about airports and user favorites. The database is a singleton,
 * meaning only one instance of the database exists at any time. The database is pre-populated using
 * a file named `flight_search.db` located in the `assets/database` directory.
 */
@Database(
    version = 1,
    entities = [
        AirportEntity::class,
        FavoriteEntity::class,
    ],
    exportSchema = false,
)
abstract class FlightSearchDatabase : RoomDatabase() {

    /**
     * @see AirportDao
     */
    abstract fun airportDao(): AirportDao

    /**
     * @see FavoriteDao
     */
    abstract fun favoriteDao(): FavoriteDao

    companion object {

        /**
         * Internal instance of the [FlightSearchDatabase].
         */
        @Volatile
        private var Instance: FlightSearchDatabase? = null

        /**
         * Returns the current instance of the [FlightSearchDatabase] if it exists, otherwise creates
         * a new instance of the database from the database file.
         *
         * @return Instance of the [FlightSearchDatabase].
         */
        fun getDatabase(context: Context): FlightSearchDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    FlightSearchDatabase::class.java,
                    "flight_search_database",
                )
                    .createFromAsset("database/flight_search.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }

}