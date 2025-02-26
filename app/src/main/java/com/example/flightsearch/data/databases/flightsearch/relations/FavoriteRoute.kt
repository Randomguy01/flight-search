package com.example.flightsearch.data.databases.flightsearch.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.flightsearch.data.databases.flightsearch.entities.AirportEntity
import com.example.flightsearch.data.databases.flightsearch.entities.FavoriteEntity
import com.example.flightsearch.domain.model.Flight

/**
 * Represents a user's favorite route, combining a [FavoriteEntity] entity with its corresponding
 * departure and destination [AirportEntity]s.
 */
data class FavoriteRoute(

    /**
     * Embedded [FavoriteEntity] entity.
     */
    @Embedded
    val favorite: FavoriteEntity,

    /**
     * The [AirportEntity] for the departure of the [FavoriteEntity] route. Relates
     * [FavoriteEntity.departureCode] to an [AirportEntity.iata].
     */
    @Relation(
        parentColumn = "departure_code",
        entityColumn = "iata_code"
    )
    val departure: AirportEntity,

    /**
     * The [AirportEntity] for the destination of the [FavoriteEntity] route. Relates
     * [FavoriteEntity.destinationCode] to an [AirportEntity.iata].
     */
    @Relation(
        parentColumn = "destination_code",
        entityColumn = "iata_code"
    )
    val destination: AirportEntity,
) {
    /**
     * Converts this [FavoriteRoute] to a [Flight] domain model.
     */
    fun toFlight() = Flight(
        id = favorite.id,
        departure = departure.toDomain(),
        destination = destination.toDomain(),
        isFavorite = true,
    )
}
