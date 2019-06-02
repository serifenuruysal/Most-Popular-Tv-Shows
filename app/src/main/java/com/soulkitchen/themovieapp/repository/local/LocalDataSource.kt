package com.soulkitchen.themovieapp.repository.local

import com.soulkitchen.themovieapp.repository.data.MovieDbResponse
import com.soulkitchen.themovieapp.repository.MovieService
import io.reactivex.Single
import org.koin.sampleapp.repository.local.JsonReader

class LocalDataSource(val jsonReader: JsonReader) :MovieService {
    override fun getPopularMovies(page: Int): Single<MovieDbResponse> {
        return Single.just(jsonReader.getMovieResult("moviedb"))
    }


}