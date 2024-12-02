package com.dicoding.mandoor.api

import com.dicoding.mandoor.response.LogMandorResponse
import com.dicoding.mandoor.response.LogUserResponse
import com.dicoding.mandoor.response.NewsResponse
import com.dicoding.mandoor.response.RegMandorResponse
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

data class LogMandorRequest(
    val Email: String,
    val Password: String
)

data class RegMandorRequest(
    val FullName: String,
    val Username: String,
    val Email: String,
    val Password: String
)


interface ApiService {
    // Register User
    @Headers("Content-Type: application/json")
    @POST("/register")
    fun registerUser(@Body request: RegUserRequest): Call<RegUserResponse>

    // Register Mandor
    @Headers("Content-Type: application/json")
    @POST("/regmandor")
    fun registerMandor(@Body request: RegMandorRequest): Call<RegMandorResponse>

    // Login User
    @Headers("Content-Type: application/json")
    @POST("/login")
    fun loginUser(@Body request: LogUserRequest): Call<LogUserResponse>

    // Login Mandor
    @Headers("Content-Type: application/json")
    @POST("/logmandor")
    fun loginMandor(@Body request: LogMandorRequest): Call<LogMandorResponse>

    // Get News
    @GET("/news")
    fun getNews(): Call<NewsResponse>
}

