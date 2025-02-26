package com.example.flightsearch.domain.model

val dummyAirport = Airport(
    id = -1,
    iata = "DMY",
    name = "Dummy International Airport",
    annualPassengers = -1,
)

/**
 * Represents an airport.
 */
data class Airport(

    /**
     * Unique identifier of the [Airport].
     */
    val id: Int,

    /**
     * The 3 letter IATA code of the [Airport].
     */
    val iata: String,

    /**
     * The name of the [Airport].
     */
    val name: String,

    /**
     * Number of passengers that fly through this [Airport] annually.
     */
    val annualPassengers: Int,
)
