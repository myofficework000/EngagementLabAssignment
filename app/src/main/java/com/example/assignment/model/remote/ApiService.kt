package com.example.assignment.model.remote

import com.example.assignment.model.response.ListOfMovieResponse
import com.example.assignment.model.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("characters/{id}")
    suspend fun getMovie(
        @Path("id") id: Int
    ): Response<MovieResponse>

    @GET("characters/")
    suspend fun getAllMovies(): Response<ListOfMovieResponse>

    companion object {
        fun getInstance(): ApiService = ApiClient.myRetrofit.create(ApiService::class.java)
    }
}