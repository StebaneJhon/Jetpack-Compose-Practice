package com.example.dynamictheme.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dynamictheme.backend.modele.Deck
import com.example.dynamictheme.ui.theme.DynamicThemeTheme
import com.example.dynamictheme.ui.theme.ThemeViewModel

@Composable
fun DeckListScreen(decks: List<Deck>, darkTheme: Boolean, viewModel: ThemeViewModel, onDeckClick: (Deck) -> Unit) {

    val selectedMode by viewModel.userThemeMode.collectAsState()

    Scaffold(
        topBar = { Text("My Library", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp)) }
    ) { padding ->
        LazyColumn(contentPadding = padding, modifier = Modifier.fillMaxSize()) {

            item {
                ThemeSelectorHeader(
                    selectedMode = selectedMode,
                    onModeSelected = {
                        viewModel.updateThemeMode(it)
                    }
                )

                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Text(
                    "Select a Deck",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.labelLarge
                )
            }

            items(decks) { deck ->
                DeckItem(deck = deck, darkTheme = darkTheme, onClick = { onDeckClick(deck) })
            }
        }
    }
}

@Composable
fun DeckItem(deck: Deck, darkTheme: Boolean, onClick: () -> Unit) {
    // We use a local Theme wrapper just for the card UI to show its color
    DynamicThemeTheme (appTheme = deck.theme, darkTheme = darkTheme) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(100.dp),
            onClick = onClick,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer // This will be BLUE or RED etc.
            )
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = deck.name,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun ThemeSelectorHeader(
    selectedMode: ThemeMode,
    onModeSelected: (ThemeMode) -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text("App Appearance", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        ThemeMode.entries.forEach { mode ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (mode == selectedMode),
                        onClick = { onModeSelected(mode) }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (mode == selectedMode),
                    onClick = { onModeSelected(mode) }
                )
                Text(
                    text = mode.name.lowercase().replaceFirstChar { it.uppercase() },
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}