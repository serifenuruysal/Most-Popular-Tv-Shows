package com.soulkitchen.themovieapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.soulkitchen.themovieapp.repository.MovieRepository
import com.soulkitchen.themovieapp.repository.MovieRepositoryImpl
import com.soulkitchen.themovieapp.repository.MovieService
import com.soulkitchen.themovieapp.repository.data.MovieDbResponse
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MovieRepositoryTest {

    private lateinit var repository: MovieRepository
    private val tvShowsObserver = TestObserver<MovieDbResponse>()
    @Mock
    private lateinit var tvShowService: MovieService
    private val gson = Gson()
    private lateinit var successResponse: MovieDbResponse

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = MovieRepositoryImpl(tvShowService)
        successResponse = successResponse()
    }

    private fun successResponse(): MovieDbResponse {
        val type = object : TypeToken<MovieDbResponse>() {}.type
        return ReadFileUtils.readResponse<MovieDbResponse>(
            javaClass.classLoader,
            type, "moviedb.json", gson
        )
    }

    @Test
    fun `get tv shows response successfully`() {

        `when`(tvShowService.getPopularMovies(0)).thenReturn(Single.just(successResponse))

        repository.getPopularTvShows(0).subscribe(tvShowsObserver)

        tvShowsObserver.assertComplete()
        tvShowsObserver.assertValueCount(1)
        val result = tvShowsObserver.values()[0]
        assertThat(result.results, `is`(successResponse.results))
    }


    @Test
    fun `get tv shows page list successfully`() {

        `when`(tvShowService.getPopularMovies(0)).thenReturn(Single.just(successResponse))

        repository.getPopularTvShows(0).subscribe(tvShowsObserver)

        tvShowsObserver.assertComplete()
        tvShowsObserver.assertValueCount(1)
        val result = tvShowsObserver.values()[0]
        assertThat(result.total_pages, `is`(successResponse.total_pages!!))
    }
}
