package com.example.flightsearch.data.databases.flightsearch.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.flightsearch.domain.model.Airport


/**
 * Represents an airport entity in the database.
 */
@Entity(
    tableName = "airport",
    indices = [
        // Each airport has a unique IATA code
        Index(value = ["iata_code"], unique = true)
    ]
)
data class AirportEntity(

    /**
     * Unique identifier (primary key)
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * 3 letter IATA code, stored as "iata_code"
     */
    @ColumnInfo(name = "iata_code")
    val iata: String,

    /**
     * Full airport name.
     */
    val name: String,

    /**
     * Number of passengers per year, stored as "passengers"
     */
    @ColumnInfo(name = "passengers")
    val passengerCount: Int,
) {
    /**
     * Converts this [AirportEntity] to a [Airport] domain model.
     */
    fun toDomain() = Airport(
        id = this.id,
        iata = this.iata,
        name = this.name,
        annualPassengers = this.passengerCount,
    )
}
