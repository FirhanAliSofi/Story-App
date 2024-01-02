package com.firhanalisofi.submissionstoryapp.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.firhanalisofi.submissionstoryapp.data.response.AddNewStoryResponse
import com.firhanalisofi.submissionstoryapp.data.response.GetAllStoryResponse
import com.firhanalisofi.submissionstoryapp.data.response.ListStoryItem
import com.firhanalisofi.submissionstoryapp.data.response.LoginResponse
import com.firhanalisofi.submissionstoryapp.data.response.RegisterResponse
import com.firhanalisofi.submissionstoryapp.data.retrofit.ApiService
import com.firhanalisofi.submissionstoryapp.data.session.LoginPreferences
import com.firhanalisofi.submissionstoryapp.helper.Result
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(private val pref: LoginPreferences, private val apiService: ApiService) {

    fun postLogin(email: String,password: String): LiveData<Result<LoginResponse>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.postLogin(email, password)
            if (response.error){
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postRegister(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postRegister(name, email, password)
            if (response.error){
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getListStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(pref, apiService)
            }
        ).liveData
    }

    fun getStoryWithMap(): LiveData<Result<GetAllStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStory(
                token = "Bearer ${pref.getUser().token}",
                page = 1,
                size = 100,
                location = 1
            )
            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun uploadImage(imageFile: MultipartBody.Part, desc: RequestBody, lat: RequestBody?, lon: RequestBody?): LiveData<Result<AddNewStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.uploadImage(
                token = "Bearer ${pref.getUser().token}",
                file = imageFile,
                description = desc,
                lat = lat,
                lon = lon
            )
            if (response.error){
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object{
        @Volatile
        private var instance: StoryRepository?= null
        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(
            pref: LoginPreferences,
            apiService: ApiService

        ): StoryRepository = instance ?: synchronized(this){
            instance ?: StoryRepository(pref, apiService)
        }.also { instance = it }
    }
}