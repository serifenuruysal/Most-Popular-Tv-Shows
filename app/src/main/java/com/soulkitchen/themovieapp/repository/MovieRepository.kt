package com.soulkitchen.themovieapp.repository

import com.soulkitchen.themovieapp.repository.data.MovieDbResponse
import io.reactivex.Single

/**
 * MovieRepository
 */
interface MovieRepository {
    fun getPopularTvShows(page: Int): Single<MovieDbResponse>
}

/**
 * MovieRepository
 * Make use of MovieService
 */
class MovieRepositoryImpl(private val movieDatasource: MovieService) : MovieRepository {

    override fun getPopularTvShows(page: Int): Single<MovieDbResponse> =
        movieDatasource.getPopularMovies(page).doOnSuccess {t ->
            if (t.page==0) {
                t.results
            }
        }


}
