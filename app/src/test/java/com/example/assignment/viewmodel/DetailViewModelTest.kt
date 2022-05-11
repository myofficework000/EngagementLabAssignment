package com.example.assignment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.assignment.model.remote.ApiService
import com.example.assignment.model.remote.Constants
import com.example.assignment.model.response.MovieResponse
import com.example.assignment.viewmodel.detail.DetailViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineExceptionHandler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.verify


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var isPBProcessing: Observer<Boolean>

    @Mock
    lateinit var resultProcessing: Observer<MovieResponse>

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
            val exceptionHandler = TestCoroutineExceptionHandler()
            launch(exceptionHandler) {

                val viewModel = DetailViewModel(apiService)

                viewModel.getMovie(6)

                viewModel.liveDataForProgress.observeForever(isPBProcessing)
                viewModel.result.observeForever(resultProcessing)

                val expectedResult =
                    Gson().fromJson(Constants.MovieID22, MovieResponse::class.java)

                verify(apiService).getMovie(MOVIE_ID)

                verify(resultProcessing).onChanged(expectedResult)
                verify(isPBProcessing).onChanged(false)

                viewModel.liveDataForProgress.removeObserver(isPBProcessing)
                viewModel.result.removeObserver(resultProcessing)
            }
        }
    }

    @Test(expected = RuntimeException::class)
    fun `movie test for exception`() {
        runBlockingTest {
            doThrow(RuntimeException("No internet")).`when`(apiService.getMovie(MOVIE_ID))

            val viewModel = DetailViewModel(apiService)

            viewModel.liveDataForProgress.observeForever(isPBProcessing)
            viewModel.result.observeForever(resultProcessing)
            viewModel.message.observeForever(messageProcessing)
            viewModel.getMovie(MOVIE_ID)

            verify(isPBProcessing).onChanged(true)
            verify(apiService).getAllMovies()
            verify(isPBProcessing).onChanged(false)

            val e = Exception("No internet")
            val expected = "Error is $e"

            verify(messageProcessing).onChanged(expected)
            viewModel.liveDataForProgress.removeObserver(isPBProcessing)
            viewModel.result.removeObserver(resultProcessing)
        }
    }

    @Test
    fun `movie test for error`() {
        runBlockingTest {
            val exceptionHandler = TestCoroutineExceptionHandler()
            launch(exceptionHandler) {
                val viewModel = DetailViewModel(apiService)
                viewModel.getMovie(MOVIE_ID)

                viewModel.message.observeForever(messageProcessing)
                viewModel.liveDataForProgress.observeForever(pbProcessing)

                pbProcessing.onChanged(true)

                verify(apiService).getMovie(MOVIE_ID)

                pbProcessing.onChanged(false)

                messageProcessing.onChanged("Something went wrong")

                viewModel.liveDataForProgress.removeObserver(pbProcessing)
                viewModel.message.removeObserver(messageProcessing)
            }
        }
    }

    companion object {
        const val MOVIE_ID = 1
    }
}