package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.ui.screens.home.HomeScreenViewModel

/**
 * Defines a [ViewModelProvider.Factory] for all [ViewModel]s in the app.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(
                airportRepository = application.container.airportRepository,
                favoriteRepository = application.container.favoriteRepository,
            )
        }
    }
}

/**
 * Extension on [CreationExtras] to retrieve the [FlightSearchApplication] instance.
 */
private val CreationExtras.application: FlightSearchApplication
    get() = (this[APPLICATION_KEY] as FlightSearchApplication)