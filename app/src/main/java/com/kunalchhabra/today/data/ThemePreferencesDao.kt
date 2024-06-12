package com.kunalchhabra.today.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kunalchhabra.today.model.ThemePreferencesEntity

@Dao
interface ThemePreferencesDao {

    @Query("SELECT * FROM theme_preferences WHERE id = 0")
    suspend fun getThemePreferences(): ThemePreferencesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThemePreferences(themePreferencesEntity: ThemePreferencesEntity)

    @Update
    suspend fun updateThemePreferences(themePreferencesEntity: ThemePreferencesEntity)
}
