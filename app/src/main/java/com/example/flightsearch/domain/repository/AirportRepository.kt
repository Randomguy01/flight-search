package com.example.flightsearch.domain.repository

import com.example.flightsearch.domain.model.Airport
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for accessing and managing [Airport] data.
 */
interface AirportRepository {

    /**
     * Creates a [Flow] of [Airport]s that match the given [query] for [Airport.name] or
     * [Airport.iata] code.
     */
    fun searchAirportsFlow(query: String): Flow<List<Airport>>

    /**
     * Creates a [Flow] of all [Airport]s ordered by [Airport.annualPassengers].
     */
    fun getAirportsFlow(): Flow<List<Airport>>
}