package com.example.flightsearch.data.databases.flightsearch.repository

import com.example.flightsearch.data.databases.flightsearch.FlightSearchDatabase
import com.example.flightsearch.data.databases.flightsearch.dao.FavoriteDao
import com.example.flightsearch.data.databases.flightsearch.entities.toFavorite
import com.example.flightsearch.domain.model.Flight
import com.example.flightsearch.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * [FavoriteRepository] implementation for accessing and managing [Flight] data from the * [FlightSearchDatabase].
 */
class RoomFavoriteRepository(private val favoriteDao: FavoriteDao) : FavoriteRepository {

    /**
     * Mutex for synchronizing access to the database.
     */
    private val favoriteMutex = Mutex()

    /**
     * Inserts a [Flight] as a favorite into the [FlightSearchDatabase].
     *
     * Sets id to 0 to allow room to generate the id.
     */
    override suspend fun createFavorite(flight: Flight) = favoriteDao.insert(
        flight.copy(id = 0).toFavorite()
    )

    /**
     * Reads a single favorite [Flight] by its [departureCode] and [destinationCode] from the
     * [FlightSearchDatabase].
     */
    override suspend fun getFavoriteByRoute(
        departureCode: String,
        destinationCode: String
    ): Flight? = favoriteDao.getFavoriteByRoute(departureCode, destinationCode)?.toFlight()

    /**
     * Creates a [Flow] of all [Flight]s that are favorites from the [FlightSearchDatabase].
     */
    override fun getFavoritesFlow(): Flow<List<Flight>> {
        return favoriteDao.getFavoriteRoutes().map {
            it.map { favoriteRoute ->
                Flight(
                    id = favoriteRoute.favorite.id,
                    departure = favoriteRoute.departure.toDomain(),
                    destination = favoriteRoute.destination.toDomain(),
                    isFavorite = true,
                )
            }
        }
    }

    /**
     * Toggles the "favorite" status of a [Flight] in the [FlightSearchDatabase]. Uses
     * [favoriteMutex] to ensure only one thread can access the database at a time.
     */
    override suspend fun toggleFavorite(flight: Flight) = favoriteMutex.withLock {
        // Get potentially existing favorite
        val favorite = getFavoriteByRoute(flight.departure.iata, flight.destination.iata)

        // Toggle favorite
        if (favorite == null) {
            // If favorite doesn't exist, create it
            createFavorite(flight)
        } else {
            // If favorite exists, remove it
            removeFavorite(flight.copy(id = favorite.id))
        }
    }

    /**
     * Deletes a [Flight] as a favorite from the [FlightSearchDatabase].
     */
    override suspend fun removeFavorite(flight: Flight) = favoriteDao.delete(flight.toFavorite())

}