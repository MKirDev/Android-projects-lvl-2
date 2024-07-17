package com.application.data

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://rickandmortyapi.com"

class CharactersDataSource {

    object RetrofitInstance {
        private val retrofit = Retrofit
            .Builder()
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                }).build()
            )
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val charactersList: CharactersApi = retrofit.create(CharactersApi::class.java)
    }

    interface CharactersApi {
        @Headers(
            "Accept: application/json",
            "Content-type: application/json"
        )
        @GET("/api/character")
        suspend fun getCharactersList(@Query("page") page: Int): com.application.data.dto.ResultsDto
    }

    fun getRetrofitInstance(): RetrofitInstance {
        return RetrofitInstance
    }
}