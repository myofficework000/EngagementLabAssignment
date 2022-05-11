package com.example.assignment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.assignment.model.remote.ApiService
import com.example.assignment.model.remote.Constants
import com.example.assignment.model.response.ListOfMovieResponse
import com.example.assignment.viewmodel.list.MainViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.verify
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var isPBProcessing: Observer<Boolean>

    @Mock
    lateinit var resultProcessing: Observer<ListOfMovieResponse>

    @Mock
    lateinit var messageProcessing: Observer<String>

    @Mock
    lateinit var pbProcessing: Observer<Boolean>

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `movie test`() {
        runBlockingTest {
            val jsonData = Gson().fromJson(Constants.ALL_MOVIE, ListOfMovieResponse::class.java)
            val response = Response.success(jsonData)
            Mockito.`when`(apiService.getAllMovies()).thenReturn(response)

            val viewModel = MainViewModel(apiService)

            viewModel.getAllMovies()

            viewModel.liveDataForProgress.observeForever(isPBProcessing)
            viewModel.resultAllMovie.observeForever(resultProcessing)

            val expectedResult =
                Gson().fromJson(Constants.ALL_MOVIE, ListOfMovieResponse::class.java)

            verify(apiService).getAllMovies()
            verify(resultProcessing).onChanged(expectedResult)
            verify(isPBProcessing).onChanged(false)

            viewModel.liveDataForProgress.removeObserver(isPBProcessing)
            viewModel.resultAllMovie.removeObserver(resultProcessing)
        }
    }

    @Test(expected = RuntimeException::class)
    fun `movie test for exception`() {
        runBlockingTest {
            doThrow(RuntimeException("No internet")).`when`(apiService.getAllMovies())

            val viewModel = MainViewModel(apiService)

            viewModel.liveDataForProgress.observeForever(isPBProcessing)
            viewModel.resultAllMovie.observeForever(resultProcessing)
            viewModel.message.observeForever(messageProcessing)
            viewModel.getAllMovies()

            verify(isPBProcessing).onChanged(true)
            verify(apiService).getAllMovies()
            verify(isPBProcessing).onChanged(false)

            val e = Exception("No internet")
            val expected = "Error is $e"

            verify(messageProcessing).onChanged(expected)
            viewModel.liveDataForProgress.removeObserver(isPBProcessing)
            viewModel.resultAllMovie.removeObserver(resultProcessing)
        }
    }

    @Test
    fun `movie test for error`() {
        runBlockingTest {
            val response = Response.error<String>(
                400,
                "Internal problem".toResponseBody("text/plain".toMediaType())
            )
            doReturn(response).`when`(apiService).getAllMovies()

            val viewModel = MainViewModel(apiService)

            viewModel.message.observeForever(messageProcessing)
            viewModel.liveDataForProgress.observeForever(pbProcessing)

            viewModel.getAllMovies()

            pbProcessing.onChanged(true)

            verify(apiService).getAllMovies()

            pbProcessing.onChanged(false)
            messageProcessing.onChanged("Something went wrong")

            viewModel.liveDataForProgress.removeObserver(pbProcessing)
            viewModel.message.removeObserver(messageProcessing)
        }
    }
}