package com.example.assignment.viewmodel.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.model.remote.ApiService
import com.example.assignment.model.response.MovieResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val apiService: ApiService) : ViewModel() {

    val result = MutableLiveData<MovieResponse>()
    val liveDataForProgress = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun getMovie(id: Int) {

        val handler = CoroutineExceptionHandler { _, _ ->
            liveDataForProgress.postValue(false)
            message.postValue("this is from Handler in Exception")
        }
        viewModelScope.launch(handler) {

            withContext(Dispatchers.IO) {

                Log.i("tag", Thread.currentThread().name)
                liveDataForProgress.postValue(true)
                val response = apiService.getMovie(id)
                if (!response.isSuccessful) {
                    liveDataForProgress.postValue(false)
                    message.postValue("Something went wrong")
                    return@withContext
                }
                if (response.body() == null) {
                    liveDataForProgress.postValue(false)
                    message.postValue("No data has found, try again")
                    return@withContext
                }
                result.postValue(response.body())   //data sent
                liveDataForProgress.postValue(false)
                message.postValue("Success")
                return@withContext
            }
        }
    }
}