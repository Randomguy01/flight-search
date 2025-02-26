package com.example.flightsearch.domain.model

val dummyFlight = Flight(
    id = -1,
    departure = dummyAirport,
    destination = dummyAirport,
    isFavorite = false,
)

/**
 * Represents a flight between two [Airport]s.
 */
data class Flight(

    /**
     * Unique identifier of the [Flight].
     */
    val id: Int,

    /**
     * The [Airport] that the flight departs from.
     */
    val departure: Airport,

    /**
     * The [Airport] that the flight arrives at.
     */
    val destination: Airport,

    /**
     * Whether or not the [Flight] is a favorite.
     */
    val isFavorite: Boolean,
)

