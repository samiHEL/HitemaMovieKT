package com.example.projethitemafilm.db

data class Film(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Double
)
