package com.zacoding.android.weather.presentation.navigation

sealed class Screens(val route: String) {
    object PokemonListingScreen : Screens("pokemon_listing_screen")
    object PokemonDetailsScreen : Screens("pokemon_details_screen")

    fun withArgs(vararg value: String): String {
        return buildString {
            append(route)
            value.forEach {
                append("/$it")

            }
        }
    }
}