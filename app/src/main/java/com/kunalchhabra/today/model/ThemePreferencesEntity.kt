package com.kunalchhabra.today.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "theme_preferences")
data class ThemePreferencesEntity(
    @PrimaryKey val id: Int = 0, // Singleton pattern
    val themeColor: String = "Pink", // Default theme color
    val isDarkTheme: Boolean,
    val dynamicColor: Boolean = false // Default dynamic color preference
)
