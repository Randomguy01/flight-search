package com.example.flightsearch.domain.repository

import com.example.flightsearch.domain.model.Flight
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for accessing and managing [Flight] data.
 */
interface FavoriteRepository {

    /**
     * Creates a favorite from a [Flight].
     */
    suspend fun createFavorite(flight: Flight)

    /**
     * Reads a single favorite [Flight] by its [departureCode] and [destinationCode].
     */
    suspend fun getFavoriteByRoute(departureCode: String, destinationCode: String): Flight?

    /**
     * Creates a [Flow] of all [Flight]s that are favorites.
     */
    fun getFavoritesFlow(): Flow<List<Flight>>

    /**
     * Toggles the "favorite" status of a [Flight].
     */
    suspend fun toggleFavorite(flight: Flight)

    /**
     * Deletes a [Flight] as a favorite.
     */
    suspend fun removeFavorite(flight: Flight)
}