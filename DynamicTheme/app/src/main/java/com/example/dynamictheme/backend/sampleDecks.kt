package com.example.dynamictheme.backend

import com.example.dynamictheme.backend.modele.Deck
import com.example.dynamictheme.ui.AppTheme

val decks = listOf(
    Deck(1, "Blue", AppTheme.BLUE),
    Deck(2, "Red", AppTheme.RED),
    Deck(3, "Yellow", AppTheme.YELLOW)
)