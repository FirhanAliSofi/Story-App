package com.firhanalisofi.submissionstoryapp.view.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.firhanalisofi.submissionstoryapp.story.StoryRepository
import com.firhanalisofi.submissionstoryapp.data.response.RegisterResponse
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
class RegisterViewModelTest {
    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var registerViewModel: RegisterViewModel
    private var dummyResponseRegister = DataDummy.generateDummySuccesRegister()

    private val dataDummyName = "name"
    private val dataDummyEmail = "email"
    private val dataDummyPassword = "password"

    @Before
    fun setup(){
        registerViewModel = RegisterViewModel(storyRepository)
    }

    @Test
    fun `when Post Register Should Not Null and Return Success`(){
        val expectedSignUp = MutableLiveData<Result<RegisterResponse>>()
        expectedSignUp.value = Result.Success(dummyResponseRegister)
        Mockito.`when`(storyRepository.postRegister(dataDummyName,dataDummyEmail,dataDummyPassword)).thenReturn(expectedSignUp)
        val actualResponse = registerViewModel.postRegister(dataDummyName,dataDummyEmail,dataDummyPassword).getOrAwaitValue()
        Mockito.verify(storyRepository).postRegister(dataDummyName,dataDummyEmail,dataDummyPassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Success)
    }

    @Test
    fun `when Post Register Should Not Null and Return Error`(){
        dummyResponseRegister = DataDummy.generateDummyErrorResgister()
        val expectedSignUp = MutableLiveData<Result<RegisterResponse>>()
        expectedSignUp.value = Result.Error("Email is already taken")
        Mockito.`when`(storyRepository.postRegister(dataDummyName,dataDummyEmail,dataDummyPassword)).thenReturn(expectedSignUp)
        val actualResponse = registerViewModel.postRegister(dataDummyName,dataDummyEmail,dataDummyPassword).getOrAwaitValue()
        Mockito.verify(storyRepository).postRegister(dataDummyName,dataDummyEmail,dataDummyPassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Error)
    }
}
