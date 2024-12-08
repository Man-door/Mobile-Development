package com.dicoding.mandoor.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL_MAIN = "https://dd40-103-162-62-10.ngrok-free.app/"
    private const val BASE_URL_SURVEY = "https://7127-182-4-101-68.ngrok-free.app/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    fun getApiService(baseUrl: String): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val mainInstance: ApiService by lazy {
        getApiService(BASE_URL_MAIN)
    }

    val surveyInstance: ApiService by lazy {
        getApiService(BASE_URL_SURVEY)
    }
}
