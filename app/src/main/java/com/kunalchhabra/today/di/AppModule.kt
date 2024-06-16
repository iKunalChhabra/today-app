package com.kunalchhabra.today.di

import android.content.Context
import androidx.room.Room
import com.kunalchhabra.today.data.ThemePreferencesDao
import com.kunalchhabra.today.data.TodoDao
import com.kunalchhabra.today.data.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideTodoDao(database: TodoDatabase): TodoDao {
        return database.todoDao()
    }

    @Provides
    @Singleton
    fun provideThemePreferencesDao(database: TodoDatabase): ThemePreferencesDao {
        return database.themePreferencesDao()
    }

    @Singleton
    @Provides
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }
}
