package com.dicoding.mandoor.api

import com.dicoding.mandoor.response.LogUserResponse
import com.dicoding.mandoor.response.NewsResponse
import com.dicoding.mandoor.response.RegUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

data class RegUserRequest(
    val FullName: String,
    val Username: String,
    val Email: String,
    val Password: String
)

data class LogUserRequest(
    val Email: String,
    val Password: String
)


interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/register")
    fun registerUser(@Body request: RegUserRequest): Call<RegUserResponse>

    @Headers("Content-Type: application/json")
    @POST("/login")
    fun loginUser(@Body request: LogUserRequest): Call<LogUserResponse>


    // Get News
    @GET("/news")
    fun getNews(): Call<NewsResponse>
}

