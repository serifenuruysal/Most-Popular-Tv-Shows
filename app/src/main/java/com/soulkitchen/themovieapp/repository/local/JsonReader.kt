package org.koin.sampleapp.repository.local

import com.soulkitchen.themovieapp.repository.data.MovieDbResponse

/**
 * Json reader
 */
interface JsonReader {
    fun getMovieResult(name: String): MovieDbResponse
}