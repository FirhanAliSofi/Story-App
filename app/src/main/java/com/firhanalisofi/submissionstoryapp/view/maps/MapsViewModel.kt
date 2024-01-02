package com.firhanalisofi.submissionstoryapp.view.maps

import androidx.lifecycle.ViewModel
import com.firhanalisofi.submissionstoryapp.story.StoryRepository

class MapsViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getStoryMap() = storyRepository.getStoryWithMap()
}