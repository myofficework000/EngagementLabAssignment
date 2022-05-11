package com.example.assignment.model.response

data class ListOfMovieResponse(
    val count: Int,
    val data: List<Data>,
    val nextPage: String,
    val totalPages: Int
)