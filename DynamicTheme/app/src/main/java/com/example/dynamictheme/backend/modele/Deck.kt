package com.example.dynamictheme.backend.modele

import com.example.dynamictheme.ui.AppTheme

data class Deck (
    val id: Int,
    val name: String,
    val theme: AppTheme
)