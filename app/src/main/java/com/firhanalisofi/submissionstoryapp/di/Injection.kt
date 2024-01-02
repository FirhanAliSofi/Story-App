package com.firhanalisofi.submissionstoryapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.firhanalisofi.submissionstoryapp.story.StoryRepository
import com.firhanalisofi.submissionstoryapp.data.retrofit.ApiConfig
import com.firhanalisofi.submissionstoryapp.data.session.LoginPreferences

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("story")

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val prefe = LoginPreferences(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(prefe, apiService)
    }
}