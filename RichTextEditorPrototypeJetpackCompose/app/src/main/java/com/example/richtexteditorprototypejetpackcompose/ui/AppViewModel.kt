package com.example.richtexteditorprototypejetpackcompose.ui

import androidx.compose.material3.Card
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

data class CardContent(
    val id: String,
    val text: String,
) {
    companion object {
        fun generateEmptyContent() = CardContent(
            id = UUID.randomUUID().toString(),
            text = ""
        )
        fun generateContent(text: String) = CardContent(
            id = UUID.randomUUID().toString(),
            text = text,
        )
    }
}

data class Card(
    val id: String,
    val contentList: List<CardContent>,
) {
    companion object {

        fun generateEmptyCard() = Card(
            id = UUID.randomUUID().toString(),
            contentList = listOf(CardContent.generateEmptyContent() )
        )

        fun generateCard(contentList: List<CardContent>) = Card(
            id = UUID.randomUUID().toString(),
            contentList = contentList
        )

    }
}

data class AppUiState(
    val cardList: List<Card> = emptyList(),
    val formingCard: Card? = null,
) {
    companion object {
        fun generateEmptyField() = Card.generateEmptyCard()
    }
}

class AppViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(formingCard = AppUiState.generateEmptyField())
        }
    }

    fun addCard(newCard: Card) {
        _uiState.update { currentState ->
            currentState.copy(cardList = currentState.cardList + newCard, formingCard = Card.generateEmptyCard())
        }
    }

    fun addField() {
        _uiState.update { currentState ->
            val formingCard = currentState.formingCard
            currentState.copy(formingCard = formingCard?.copy(contentList = formingCard.contentList + CardContent.generateEmptyContent()))
        }
    }

    fun updateField(newContent: CardContent) {
        _uiState.update {
            val newContentList = it.formingCard?.contentList?.map { existingContent ->
                if (existingContent.id == newContent.id) {
                    newContent
                } else {
                    existingContent
                }
            }
            it.copy(formingCard = it.formingCard?.copy(contentList = newContentList!!))
        }
    }

}