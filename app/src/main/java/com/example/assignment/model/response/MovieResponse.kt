package com.example.assignment.model.response

data class MovieResponse(
    val _id: Int,
    val allies: List<Any>,
    val enemies: List<Any>,
    val films: List<String>,
    val imageUrl: String,
    val name: String,
    val parkAttractions: List<Any>,
    val shortFilms: List<Any>,
    val tvShows: List<Any>,
    val url: String,
    val videoGames: List<Any>
)