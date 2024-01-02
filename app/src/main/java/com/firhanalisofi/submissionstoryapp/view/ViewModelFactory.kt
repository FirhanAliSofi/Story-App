package com.firhanalisofi.submissionstoryapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.firhanalisofi.submissionstoryapp.story.StoryRepository
import com.firhanalisofi.submissionstoryapp.di.Injection
import com.firhanalisofi.submissionstoryapp.view.add.AddStoryViewModel
import com.firhanalisofi.submissionstoryapp.view.login.LoginViewModel
import com.firhanalisofi.submissionstoryapp.view.main.MainViewModel
import com.firhanalisofi.submissionstoryapp.view.maps.MapsViewModel
import com.firhanalisofi.submissionstoryapp.view.register.RegisterViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

class ViewModelFactory private constructor(private val storyRepository: StoryRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(storyRepository) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(storyRepository) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(storyRepository) as T
        }
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)){
            return MapsViewModel(storyRepository) as T
        }
        if (modelClass.isAssignableFrom(AddStoryViewModel::class.java)){
            return AddStoryViewModel(storyRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }
}