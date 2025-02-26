package com.example.flightsearch.data.databases.flightsearch.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.flightsearch.data.databases.flightsearch.FlightSearchDatabase
import com.example.flightsearch.data.databases.flightsearch.entities.FavoriteEntity
import com.example.flightsearch.data.databases.flightsearch.relations.FavoriteRoute
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for interacting with the [FavoriteEntity] entity in the [FlightSearchDatabase].
 */
@Dao
interface FavoriteDao {

    /**
     * Inserts a [FavoriteEntity] into the [FlightSearchDatabase]. If the [FavoriteEntity] already exists, the
     * conflict is ignored using [OnConflictStrategy.IGNORE].
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: FavoriteEntity)


    @Transaction
    @Query("SELECT * FROM favorite WHERE departure_code = :departureCode AND destination_code = :destinationCode LIMIT 1")
    suspend fun getFavoriteByRoute(departureCode: String, destinationCode: String): FavoriteRoute?

    /**
     * Creates a [Flow] of all [FavoriteRoute]s from the [FlightSearchDatabase].
     */
    @Transaction
    @Query("SELECT * FROM favorite")
    fun getFavoriteRoutes(): Flow<List<FavoriteRoute>>

    /**
     * Deletes a [FavoriteEntity] from the [FlightSearchDatabase].
     */
    @Delete
    suspend fun delete(favorite: FavoriteEntity)

}