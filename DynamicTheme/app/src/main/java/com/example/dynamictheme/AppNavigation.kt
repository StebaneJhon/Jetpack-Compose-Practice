package com.example.dynamictheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dynamictheme.backend.decks
import com.example.dynamictheme.ui.CardListScreen
import com.example.dynamictheme.ui.DeckListScreen
import com.example.dynamictheme.ui.theme.DynamicThemeTheme
import com.example.dynamictheme.ui.theme.ThemeViewModel

@Composable
fun AppNavigation(themeViewModel: ThemeViewModel = viewModel(), darkTheme: Boolean) {

    val navController = rememberNavController()
    val selectedTheme by themeViewModel.currentAppTheme.collectAsState()

    // The entire app is wrapped in the Dynamic Theme
        NavHost(navController = navController, startDestination = "library") {

            // Screen 1: Library
            composable("library") {
                // When we return to the library, we might want to reset the theme
                LaunchedEffect(Unit) { themeViewModel.resetTheme() }

                DeckListScreen(decks = decks, darkTheme = darkTheme, viewModel = themeViewModel, onDeckClick = { deck ->
                    themeViewModel.updateAppTheme(deck.theme)
                    navController.navigate("deck_detail")
                })
            }

            // Screen 2: Deck Detail
            composable("deck_detail") {
                CardListScreen()
            }
        }
}