package com.firhanalisofi.submissionstoryapp.view.login

import androidx.lifecycle.ViewModel
import com.firhanalisofi.submissionstoryapp.story.StoryRepository

class LoginViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postLogin(email: String, password:String) = storyRepository.postLogin(email, password)
}