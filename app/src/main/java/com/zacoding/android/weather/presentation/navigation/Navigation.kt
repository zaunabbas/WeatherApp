package com.zacoding.android.weather.presentation.navigation

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zacoding.android.weather.presentation.ui.home.HomeScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()
    val todayLazyListState = rememberLazyListState()

    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(route = Screens.HomeScreen.route) {

            HomeScreen(
                todayLazyListState = todayLazyListState,
            )
        }
    }

}