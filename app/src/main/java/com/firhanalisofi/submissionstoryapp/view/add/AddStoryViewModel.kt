package com.firhanalisofi.submissionstoryapp.view.add

import androidx.lifecycle.ViewModel
import com.firhanalisofi.submissionstoryapp.story.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun uploadImage(imageFile: MultipartBody.Part, desc: RequestBody, lat: RequestBody?, lon: RequestBody?) =
        storyRepository.uploadImage(imageFile, desc, lat, lon)
}