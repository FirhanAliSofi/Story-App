package com.firhanalisofi.submissionstoryapp.view.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.firhanalisofi.submissionstoryapp.story.StoryRepository
import com.firhanalisofi.submissionstoryapp.data.response.GetAllStoryResponse
import com.firhanalisofi.submissionstoryapp.utils.DataDummy
import com.firhanalisofi.submissionstoryapp.helper.Result
import com.firhanalisofi.submissionstoryapp.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    private var dataDummy = DataDummy.generateDummyDataStory()

    @Before
    fun setup(){
        mapsViewModel = MapsViewModel(storyRepository)
    }

    @Test
    fun `when Get Story Should Not Null and Return Success`() {
        val expectedResponse = MutableLiveData<Result<GetAllStoryResponse>>()
        expectedResponse.value = Result.Success(dataDummy)
        Mockito.`when`(storyRepository.getStoryWithMap()).thenReturn(expectedResponse)
        val actual = mapsViewModel.getStoryMap().getOrAwaitValue()
        Mockito.verify(storyRepository).getStoryWithMap()
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
    }

    @Test
    fun `when Get Story Should Not Null and Return Error`() {
        dataDummy = DataDummy.generateDummyErrorDataStory()
        val expected = MutableLiveData<Result<GetAllStoryResponse>>()
        expected.value = Result.Error("error")
        Mockito.`when`(storyRepository.getStoryWithMap()).thenReturn(expected)
        val actual = mapsViewModel.getStoryMap().getOrAwaitValue()
        Mockito.verify(storyRepository).getStoryWithMap()
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Error)
    }

}