package com.example.assignment.viewmodel.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.model.remote.ApiService
import com.example.assignment.model.response.ListOfMovieResponse
import com.example.assignment.model.response.MovieResponse
import kotlinx.coroutines.*
import java.lang.Exception

class MainViewModel(private val apiService: ApiService) : ViewModel() {

    val result = MutableLiveData<MovieResponse>()
    val resultAllMovie = MutableLiveData<ListOfMovieResponse>()
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

    fun getAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getAllMovies()
                if (!response.isSuccessful) {
                    liveDataForProgress.postValue(false)
                    message.postValue("Something went wrong")
                    return@launch
                }
                if (response.body() == null) {
                    liveDataForProgress.postValue(false)
                    message.postValue("No data has found, try again")
                    return@launch
                }
                resultAllMovie.postValue(response.body())
                liveDataForProgress.postValue(false)
                message.postValue("Success")
                return@launch
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}