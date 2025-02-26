package com.example.flightsearch.ui.screens.home

import com.example.flightsearch.domain.model.Airport
import com.example.flightsearch.domain.model.Flight

/**
 * StateHolder for the [HomeScreen].
 *
 * @property searchText The current query the user has typed into the search bar.
 * @property airportSuggestions The list of airports that match the [searchText] query.
 * @property selectedAirport The airport that the user has selected from the [airportSuggestions] list.
 * @property routes The list of possible routes from the [selectedAirport].
 * @property favoriteFlights The list of favorite routes.
 */
data class HomeScreenUiState(
    val searchText: String = "",
    val airportSuggestions: List<Airport> = emptyList(),
    val selectedAirport: Airport? = null,
    val routes: List<Flight> = emptyList(),
    val favoriteFlights: List<Flight> = emptyList(),
)
