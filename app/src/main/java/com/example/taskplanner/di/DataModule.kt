package com.example.taskplanner.di

import android.content.Context
import androidx.room.Room
import com.example.taskplanner.data.AppDatabase
import com.example.taskplanner.data.dao.TaskDao
import com.example.taskplanner.storage.SharedPreferencesStorage
import com.example.taskplanner.storage.Storage
import com.example.taskplanner.util.DATABASE_NAME
import com.example.taskplanner.util.SHARED_PREFERENCES_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideStorage(@ApplicationContext context: Context): Storage {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        return SharedPreferencesStorage(sharedPreferences)
    }



    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(appDatabase: AppDatabase): TaskDao {
        return appDatabase.taskDao()
    }

}