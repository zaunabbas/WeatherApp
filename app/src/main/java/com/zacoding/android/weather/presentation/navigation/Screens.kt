package com.zacoding.android.weather.presentation.navigation

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home_screen")

    fun withArgs(vararg value: String): String {
        return buildString {
            append(route)
            value.forEach {
                append("/$it")

            }
        }
    }
}