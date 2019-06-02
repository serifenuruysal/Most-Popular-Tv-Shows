package org.koin.sampleapp.repository.local

import com.google.gson.Gson
import com.soulkitchen.themovieapp.repository.data.MovieDbResponse

/**
 * Common parts for Json reader
 */
abstract class BaseReader : JsonReader {

    private val gson = Gson()
    private val json_file = ".json"


    override fun getMovieResult(name: String): MovieDbResponse = gson.fromJson<MovieDbResponse>(readJsonFile( name + json_file), MovieDbResponse::class.java)

    abstract fun getAllFiles(): List<String>

    abstract fun readJsonFile(jsonFile: String): String
}