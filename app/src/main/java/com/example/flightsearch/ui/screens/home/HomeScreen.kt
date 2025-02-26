package com.example.flightsearch.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.ui.AppViewModelProvider
import com.example.flightsearch.ui.theme.FlightSearchTheme

/**
 * Main screen of the app, displays a [FlightSearchTextField] for searching flights, your favorite
 * routes, or a list of airports based on your search.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    // Observe the UI state
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { HomeScreenAppBar() },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Search Field
            FlightSearchTextField(
                value = uiState.searchText,
                onValueChange = { text ->
                    viewModel.setSearchText(text)
                    viewModel.setSelectedAirport(null)
                },
            )

            if (uiState.selectedAirport != null) {
                // Airport Routes
                Text(
                    text = stringResource(
                        R.string.homeScreen_flightsFrom_airport,
                        uiState.selectedAirport!!.iata
                    ),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
                FlightList(
                    flights = uiState.routes,
                    onFavoriteClicked = viewModel::onFavoriteClicked,
                )
            } else if (uiState.searchText.isEmpty()) {
                // Favorite Routes
                Text(
                    text = stringResource(R.string.homeScreen_favoriteFlights),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
                FlightList(
                    flights = uiState.favoriteFlights,
                    onFavoriteClicked = viewModel::onFavoriteClicked,
                )
            } else {
                // Search Suggestions
                AirportSuggestionList(
                    airportSuggestions = uiState.airportSuggestions,
                    onSuggestionClick = { airport ->
                        // Change search text to IATA code
                        viewModel.setSearchText(airport.iata)
                        // Update ViewModel
                        viewModel.setSelectedAirport(airport)
                    },
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }
    }
}

/**
 * A [TopAppBar] with the app's name as the title and the primary color as the background color.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeScreenAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = modifier
    )
}

@Composable
@Preview
private fun AppBarPreview() {
    FlightSearchTheme {
        HomeScreenAppBar()
    }
}

/**
 * A [CircleShape]d [TextField] for searching flights.
 *
 * @param value The current value of the [TextField].
 * @param onValueChange Called when the value of the [TextField] changes.
 */
@Composable
private fun FlightSearchTextField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
) {
    TextField(
        value = value,
        placeholder = { Text(stringResource(R.string.homeScreen_placeholder_searchText)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = Icons.Filled.Search.name,
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Mic,
                contentDescription = Icons.Filled.Mic.name,
            )
        },
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
@Preview
private fun TextPreview() {
    FlightSearchTheme {
        FlightSearchTextField(
            value = "",
        )
    }
}

