package com.kunalchhabra.today.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunalchhabra.today.model.ThemePreferencesEntity
import com.kunalchhabra.today.repository.ThemePreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val repository: ThemePreferencesRepository
) : ViewModel() {

    private val _themePreferences = MutableStateFlow<ThemePreferencesEntity?>(null)
    val themePreferences: StateFlow<ThemePreferencesEntity?> = _themePreferences.asStateFlow()

    init {
        fetchThemePreferences()
    }

    private fun fetchThemePreferences() {
        viewModelScope.launch {
            _themePreferences.value =
                repository.getThemePreferences() ?: ThemePreferencesEntity(useBlueTheme = false, isDarkTheme = false)
        }
    }

    fun saveThemePreferences(useBlueTheme: Boolean, isDarkTheme: Boolean) {
        viewModelScope.launch {
            repository.saveThemePreferences(
                _themePreferences.value?.copy(
                    useBlueTheme = useBlueTheme,
                    isDarkTheme = isDarkTheme
                ) ?: ThemePreferencesEntity(useBlueTheme = useBlueTheme, isDarkTheme = isDarkTheme)
            )
            fetchThemePreferences()
        }
    }
}
