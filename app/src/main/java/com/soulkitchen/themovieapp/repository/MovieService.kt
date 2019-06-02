package com.soulkitchen.themovieapp.repository

import com.soulkitchen.themovieapp.repository.data.MovieDbResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService{
    @GET("tv/popular")
     fun getPopularMovies(@Query("page") page: Int): Single<MovieDbResponse>
}