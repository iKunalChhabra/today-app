package com.kunalchhabra.today.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "theme_preferences")
data class ThemePreferencesEntity(
    @PrimaryKey val id: Int = 0, // Singleton pattern
    val useBlueTheme: Boolean,
    val isDarkTheme: Boolean
)
