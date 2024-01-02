package com.firhanalisofi.submissionstoryapp.view.main

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.firhanalisofi.submissionstoryapp.story.StoryRepository
import com.firhanalisofi.submissionstoryapp.data.response.ListStoryItem

class MainViewModel(storyRepository: StoryRepository): ViewModel() {
    val getAllStory: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getListStory().cachedIn(viewModelScope)
}