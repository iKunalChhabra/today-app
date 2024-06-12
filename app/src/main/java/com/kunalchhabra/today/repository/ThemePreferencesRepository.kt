package com.kunalchhabra.today.repository

import com.kunalchhabra.today.data.ThemePreferencesDao
import com.kunalchhabra.today.model.ThemePreferencesEntity
import javax.inject.Inject

class ThemePreferencesRepository @Inject constructor(private val themePreferencesDao: ThemePreferencesDao) {

    suspend fun getThemePreferences(): ThemePreferencesEntity? {
        return themePreferencesDao.getThemePreferences()
    }

    suspend fun saveThemePreferences(themePreferencesEntity: ThemePreferencesEntity) {
        themePreferencesDao.insertThemePreferences(themePreferencesEntity)
    }
}
