package com.dicoding.mandoor.api

import android.net.Uri
import com.dicoding.mandoor.response.AccountDeleteResponse
import com.dicoding.mandoor.response.AccountGETResponse
import com.dicoding.mandoor.response.AccountPUTResponse
import com.dicoding.mandoor.response.LogUserResponse
import com.dicoding.mandoor.response.MandorResponseItem
import com.dicoding.mandoor.response.NewsResponse
import com.dicoding.mandoor.response.RegUserResponse
import com.dicoding.mandoor.response.SurveyGETResponse
import com.dicoding.mandoor.response.SurveyPOSTResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

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

data class SurveyRequest(
    val Rating: String,
    val Pengalaman: String,
    val Portofolio: String,
    val Alamat: String,
    val layanan_lain: String,
    val Tanggal: String,
    val image: Uri,
    val Budget: String,
    val Deskripsi: String
)

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/register")
    fun registerUser(@Body request: RegUserRequest): Call<RegUserResponse>

    @Headers("Content-Type: application/json")
    @POST("/login")
    fun loginUser(@Body request: LogUserRequest): Call<LogUserResponse>

    @GET("/news")
    fun getNews(): Call<NewsResponse>

    @Headers("Content-Type: application/json")
    @POST("/survey")
    fun sendSurvey(
        @Header("Authorization") token: String,
        @Body request: SurveyRequest
    ): Call<SurveyPOSTResponse>


    @GET("/survey")
    fun getSurvey(@Header("Authorization") token: String): Call<SurveyGETResponse>


    @GET("/filtermandor")
    fun getMandor(@Header("Authorization") token: String): Call<List<MandorResponseItem>>



    @GET("/data")
    fun getAccount(
        @Header("Authorization") token: String,
        @Body request: AccountGETResponse
    ): Call<AccountGETResponse>

    @PUT("/data")
    fun updateAccount(
        @Header("Authorization") token: String,
        @Body request: AccountPUTResponse
    ): Call<AccountGETResponse>

    @DELETE("/data")
    fun deleteAccount(
        @Header("Authorization") token: String,
        @Body request: AccountDeleteResponse
    ): Call<AccountGETResponse>

}

