package com.example.flightsearch.data.databases.flightsearch.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.flightsearch.domain.model.Flight

/**
 * Represents a user's favorite flight route in the database.
 */
@Entity(
    tableName = "favorite",
    indices = [
        Index(value = ["departure_code", "destination_code"], unique = true)
    ]
)
data class FavoriteEntity(

    /**
     * Unique identifier (primary key).
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * IATA code for departure, stored as "departure_code".
     */
    @ColumnInfo(name = "departure_code")
    val departureCode: String,

    /**
     * IATA code for destination, stored as "destination_code".
     */
    @ColumnInfo(name = "destination_code")
    val destinationCode: String,
)

/**
 * Extension function to convert a [Flight] to a [FavoriteEntity].
 */
fun Flight.toFavorite(id: Int = this.id) = FavoriteEntity(
    id = id,
    departureCode = departure.iata,
    destinationCode = destination.iata,
)