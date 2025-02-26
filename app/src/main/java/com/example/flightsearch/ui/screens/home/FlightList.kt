package com.example.flightsearch.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.domain.model.Flight
import com.example.flightsearch.domain.model.dummyFlight
import com.example.flightsearch.ui.theme.FlightSearchTheme

/**
 * Displays a list of [Flight]s.
 */
@Composable
fun FlightList(
    flights: List<Flight>,
    modifier: Modifier = Modifier,
    onFavoriteClicked: (Flight) -> Unit = {},
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        items(flights) { flight ->
            FlightCard(
                flight = flight,
                onFavoriteClicked = { onFavoriteClicked(flight) },
            )
        }
    }
}

@Preview
@Composable
private fun FlightListPreview() {
    FlightSearchTheme {
        FlightList(List(5) { dummyFlight.copy(isFavorite = it % 2 == 0) })
    }
}

/**
 * Displays information about a [Flight] in a [Card] and allows the user to toggle the flight as a
 * favorite.
 */
@Composable
fun FlightCard(
    flight: Flight,
    modifier: Modifier = Modifier,
    onFavoriteClicked: () -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                // Departure
                FlightMovementText(stringResource(R.string.flightCard_flightMovement_depart))
                AirportListItem(flight.departure)
                // Blank Space
                Box(modifier = Modifier.height(8.dp))
                // Destination
                FlightMovementText(stringResource(R.string.flightCard_flightMovement_arrive))
                AirportListItem(flight.destination)
            }
            // Favorite Button
            Box(
                contentAlignment = Alignment.Center,
            ) {
                FavoriteButton(isFavorite = flight.isFavorite, onClick = onFavoriteClicked)
            }
        }
    }
}

/**
 * An [IconButton] that toggles the [isFavorite] state. When [isFavorite] is true, the icon
 */
@Composable
private fun FavoriteButton(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    favoriteColor: Color = MaterialTheme.colorScheme.tertiary,
    unfavoriteColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            tint = if (isFavorite) favoriteColor else unfavoriteColor,
            contentDescription = Icons.Filled.Star.name,
        )
    }
}

@Preview
@Composable
private fun FlightCardPreview() {
    FlightSearchTheme {
        FlightCard(dummyFlight)
    }
}

/**
 * [Text] that indicates the direction of the flight, either "depart" or "arrive".
 */
@Composable
private fun FlightMovementText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier,
    )
}
