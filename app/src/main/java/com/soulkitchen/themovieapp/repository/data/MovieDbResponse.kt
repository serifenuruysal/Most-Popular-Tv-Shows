package com.soulkitchen.themovieapp.repository.data

data class MovieDbResponse(

    val page: Int,
    val results: List<Results>,
    val total_results: Int,
    val total_pages: Int
)