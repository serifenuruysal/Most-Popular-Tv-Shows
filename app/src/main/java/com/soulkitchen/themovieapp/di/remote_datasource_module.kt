package org.koin.sampleapp.di

import com.soulkitchen.themovieapp.repository.MovieDataSource
import com.soulkitchen.themovieapp.repository.MovieService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import org.koin.sampleapp.di.DatasourceProperties.SERVER_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val remoteDatasourceModule = applicationContext {
    // provided web components
    bean { createOkHttpClient() }

    // Fill property
    bean { createWebService<MovieService>(get(), getProperty(SERVER_URL)) }

    bean { MovieDataSource(get(),get()) as MovieService}
}


object DatasourceProperties {
    const val SERVER_URL = "https://api.themoviedb.org/3/"
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}
