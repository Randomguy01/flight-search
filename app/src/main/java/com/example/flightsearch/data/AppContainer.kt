package com.example.flightsearch.data

import android.content.Context
import com.example.flightsearch.data.databases.flightsearch.FlightSearchDatabase
import com.example.flightsearch.data.databases.flightsearch.repository.RoomAirportRepository
import com.example.flightsearch.data.databases.flightsearch.repository.RoomFavoriteRepository
import com.example.flightsearch.domain.repository.AirportRepository
import com.example.flightsearch.domain.repository.FavoriteRepository

/**
 * Dependency injection container for app-wide dependencies.
 */
interface AppContainer {

    /**
     * See [AirportRepository].
     */
    val airportRepository: AirportRepository

    /**
     * See [FavoriteRepository].
     */
    val favoriteRepository: FavoriteRepository
}

/**
 * Implementation of [AppContainer] that provides dependencies from the [FlightSearchDatabase].
 */
class DefaultAppContainer(private val context: Context) : AppContainer {

    /**
     * Defaults to [RoomAirportRepository].
     */
    override val airportRepository: AirportRepository by lazy {
        RoomAirportRepository(FlightSearchDatabase.getDatabase(context).airportDao())
    }

    /**
     * Defaults to [RoomAirportRepository].
     */
    override val favoriteRepository: FavoriteRepository by lazy {
        RoomFavoriteRepository(FlightSearchDatabase.getDatabase(context).favoriteDao())
    }
}