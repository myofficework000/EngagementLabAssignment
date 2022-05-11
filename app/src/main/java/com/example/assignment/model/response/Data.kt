package com.example.assignment.model.response

data class Data(
    val _id: Int,
    val allies: List<Any>,
    val enemies: List<Any>,
    val films: List<String>,
    val imageUrl: String,
    val name: String,
    val parkAttractions: List<String>,
    val shortFilms: List<String>,
    val tvShows: List<String>,
    val url: String,
    val videoGames: List<String>
)