package com.example.dynamictheme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dynamictheme.ui.ThemeMode
import com.example.dynamictheme.ui.theme.DynamicThemeTheme
import com.example.dynamictheme.ui.theme.ThemeViewModel

class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                val currentDeckTheme by themeViewModel.currentAppTheme.collectAsState()
                val userMode by themeViewModel.userThemeMode.collectAsState()

                val isDark = when (userMode) {
                    ThemeMode.LIGHT -> false
                    ThemeMode.DARK -> true
                    ThemeMode.SYSTEM -> isSystemInDarkTheme()
                }

                DynamicThemeTheme(appTheme = currentDeckTheme, darkTheme = isDark) {
                    AppNavigation(themeViewModel = themeViewModel, darkTheme = isDark)
                }
        }
    }
}
