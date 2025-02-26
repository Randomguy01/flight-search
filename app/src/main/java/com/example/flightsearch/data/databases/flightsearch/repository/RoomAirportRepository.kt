package com.example.flightsearch.data.databases.flightsearch.repository

import com.example.flightsearch.data.databases.flightsearch.FlightSearchDatabase
import com.example.flightsearch.data.databases.flightsearch.dao.AirportDao
import com.example.flightsearch.data.databases.flightsearch.entities.AirportEntity
import com.example.flightsearch.domain.repository.AirportRepository
import com.example.flightsearch.domain.model.Airport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * [AirportRepository] implementation for accessing and managing [Airport] data from the
 * [FlightSearchDatabase].
 */
class RoomAirportRepository(private val airportDao: AirportDao) : AirportRepository {

    /**
     * Creates a [Flow] of [Airport]s from the [FlightSearchDatabase] that match the given [query]
     * for [AirportEntity.name] or [AirportEntity.iata] code.
     */
    override fun searchAirportsFlow(query: String): Flow<List<Airport>> {
        return airportDao.getAirportsByName(query).map { airports ->
            airports.map { it.toDomain() }
        }
    }

    /**
     * Creates a [Flow] of all [Airport]s from the [FlightSearchDatabase] ordered by
     * [AirportEntity.passengerCount].
     */
    override fun getAirportsFlow(): Flow<List<Airport>> = airportDao.getAll().map { airports ->
        airports.map { it.toDomain() }
    }
}