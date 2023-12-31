package com.firhanalisofi.submissionstoryapp.view.register

import androidx.lifecycle.ViewModel
import com.firhanalisofi.submissionstoryapp.story.StoryRepository

class RegisterViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postRegister(name: String,email:String,password:String)= storyRepository.postRegister(name, email, password)
}