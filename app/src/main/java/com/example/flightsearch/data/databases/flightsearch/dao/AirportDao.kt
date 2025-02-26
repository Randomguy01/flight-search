package com.example.flightsearch.data.databases.flightsearch.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.flightsearch.data.databases.flightsearch.FlightSearchDatabase
import com.example.flightsearch.data.databases.flightsearch.entities.AirportEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for interacting with the [AirportEntity] entity in the [FlightSearchDatabase].
 */
@Dao
interface AirportDao {

    /**
     * Creates a [Flow] of [AirportEntity]s from the [FlightSearchDatabase] that match the given [name],
     * ordered by number of passengers.
     */
    @Query("SELECT * FROM airport WHERE name LIKE :name OR iata_code LIKE :name ORDER BY passengers DESC")
    fun getAirportsByName(name: String): Flow<List<AirportEntity>>

    /**
     * Creates a [Flow] of all [AirportEntity]s from the [FlightSearchDatabase] ordered by number of
     * passengers.
     */
    @Query("SELECT * FROM airport ORDER BY passengers DESC")
    fun getAll(): Flow<List<AirportEntity>>
}