package com.example.dynamictheme.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamictheme.backend.ThemeRepository
import com.example.dynamictheme.ui.AppTheme
import com.example.dynamictheme.ui.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel (
    application: Application
): AndroidViewModel(application) {

    private val repository = ThemeRepository(application)

    private val _currentAppTheme = MutableStateFlow(AppTheme.DEFAULT)
    val currentAppTheme: StateFlow<AppTheme> = _currentAppTheme.asStateFlow()

    val userThemeMode: StateFlow<ThemeMode> = repository.themeModeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ThemeMode.SYSTEM
        )

    fun updateAppTheme(newTheme: AppTheme) {
        _currentAppTheme.value = newTheme
    }

    fun resetTheme() {
        _currentAppTheme.value = AppTheme.DEFAULT
    }

    fun updateThemeMode(newMode: ThemeMode) {
        viewModelScope.launch {
            repository.saveThemeMode(newMode)
        }
    }

}