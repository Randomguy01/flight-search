package com.example.flightsearch.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.domain.model.Airport
import com.example.flightsearch.domain.model.dummyAirport
import com.example.flightsearch.ui.theme.FlightSearchTheme

/**
 * Displays a list of [Airport]s.
 */
@Composable
fun AirportSuggestionList(
    airportSuggestions: List<Airport>,
    modifier: Modifier = Modifier,
    onSuggestionClick: (Airport) -> Unit = {}
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(airportSuggestions) { airport ->
            AirportListItem(
                airport = airport,
                modifier = Modifier.clickable { onSuggestionClick(airport) }
            )
        }
    }
}

@Preview
@Composable
private fun AirportListPreview() {
    FlightSearchTheme {
        AirportSuggestionList(List(5) { dummyAirport })
    }
}

/**
 * A [Row] of [Text]s that display the [Airport]'s [Airport.iata] and [Airport.name].
 */
@Composable
fun AirportListItem(airport: Airport, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        // IATA code
        Text(
            text = airport.iata,
            style = MaterialTheme.typography.titleMedium,
        )
        // Blank Space
        Box(modifier = Modifier.width(8.dp))
        // Airport name
        Text(
            text = airport.name,
            style = MaterialTheme.typography.bodyLarge,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun AirportPreview() {
    FlightSearchTheme {
        AirportListItem(dummyAirport)
    }
}