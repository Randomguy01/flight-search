package com.example.flightsearch.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.domain.model.Airport
import com.example.flightsearch.domain.model.Flight
import com.example.flightsearch.domain.repository.AirportRepository
import com.example.flightsearch.domain.repository.FavoriteRepository
import com.example.flightsearch.util.listStateIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Handles business logic for the [HomeScreen].
 *
 * @property airportRepository Dependency injected repository for interacting with airports.
 * @property favoriteRepository Dependency injected repository for interacting with favorites.
 */
class HomeScreenViewModel(
    private val airportRepository: AirportRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    /**
     * Creates a [StateFlow] of the user's search query, defaults to empty [String].
     */
    private val searchTextFlow = MutableStateFlow("")

    /**
     * Creates a [StateFlow] of the user's selected [Airport], defaults to null.
     */
    private val selectedAirportFlow = MutableStateFlow<Airport?>(null)

    /**
     * Creates a [StateFlow] of [Airport]s that match the user's search query. The search query is
     * delayed by 300ms before the search is performed to avoid excess queries. [flatMapLatest] is
     * used to prevent excess emissions. If the search query is empty, an empty list is emitted by
     * default.
     */
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private val suggestionsFlow = searchTextFlow.debounce(300).flatMapLatest { searchText ->
        // If the search query is empty, emit an empty list
        if (searchText.isEmpty()) flowOf(emptyList())
        // Query the repository for airports that match the search query
        else airportRepository.searchAirportsFlow("%$searchText%")
    }.listStateIn(viewModelScope)

    /**
     * Creates a [StateFlow] of the user's favorite [Flight]s from the [FavoriteRepository].
     */
    private val favoritesFlow = favoriteRepository.getFavoritesFlow().listStateIn(viewModelScope)

    /**
     * Creates a [StateFlow] of available [Airport]s from the [AirportRepository].
     */
    private val airportsFlow = airportRepository.getAirportsFlow().listStateIn(viewModelScope)

    /**
     * Creates a [StateFlow] by [combine]ing the user's selected [Airport], the available [Airport]s,
     * and the user's favorite [Flight]s to create a list of available [Flight]s from the
     * [HomeScreenUiState.selectedAirport] to all other [Airport]s. If
     * [HomeScreenUiState.selectedAirport] is null, defaults to an [emptyList].
     */
    private val airportRoutesFlow = combine(
        selectedAirportFlow,
        airportsFlow,
        favoritesFlow,
    ) { selectedAirport, airports, favoriteFlights ->
        // If the selected airport is null, emit an empty list
        if (selectedAirport == null) emptyList<Flight>()
        // Create a list of all flights (routes) from the selected airport to all other airports
        else airports
            // Filter out selected airport
            .filter { it != selectedAirport }
            // Map airports to flights
            .map { destination ->
                Flight(
                    id = destination.id,
                    departure = selectedAirport,
                    destination = destination,
                    isFavorite = favoriteFlights.any { favorite ->
                        // Determine if favorite flight
                        favorite.departure == selectedAirport && favorite.destination == destination
                    }
                )
            }
    }.listStateIn(viewModelScope)

    /**
     * Creates a [StateFlow] of [HomeScreenUiState] by [combine]ing the user's search query
     * [searchTextFlow], the autocomplete suggestions, [suggestionsFlow], the user's selected
     * [Airport], [selectedAirportFlow], the available [Airport]s, [airportsFlow], and the user's
     * favorite [Flight]s, [favoritesFlow] to create a [HomeScreenUiState].
     */
    val uiState = combine(
        searchTextFlow,
        suggestionsFlow,
        selectedAirportFlow,
        airportRoutesFlow,
        favoritesFlow,
    ) { searchText, suggestions, selectedAirport, routes, favorites ->
        HomeScreenUiState(
            searchText = searchText,
            airportSuggestions = suggestions,
            selectedAirport = selectedAirport,
            routes = routes,
            favoriteFlights = favorites,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeScreenUiState(),
    )

    /**
     * Updates the user's search query.
     */
    fun setSearchText(text: String) {
        searchTextFlow.update { text }
    }

    /**
     * Sets the user's selected [Airport], or null if no [Airport] is selected.
     */
    fun setSelectedAirport(airport: Airport?) {
        selectedAirportFlow.update { airport }
    }

    /**
     * Toggles the user's favorite status of a [Flight].
     *
     * @see [FavoriteRepository.toggleFavorite]
     */
    fun onFavoriteClicked(flight: Flight) = viewModelScope.launch {
        favoriteRepository.toggleFavorite(flight)
    }

}