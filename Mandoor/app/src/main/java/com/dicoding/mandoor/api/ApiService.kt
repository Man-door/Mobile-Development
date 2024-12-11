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
import com.dicoding.mandoor.response.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

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
    val Rating: Int,
    val Pengalaman: Int,
    val Portofolio: Int,
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

    @Multipart
    @POST("/survey")
    fun sendSurvey(
        @Header("Authorization") token: String,
        @Part("Rating") Rating: RequestBody,
        @Part("Pengalaman") Pengalaman: RequestBody,
        @Part("Portofolio") Portofolio: RequestBody,
        @Part("layanan_lain") layanan_Lain: RequestBody,
        @Part("Budget") Budget: RequestBody,
        @Part("Deskripsi") Deskripsi: RequestBody,
        @Part("Alamat") Alamat: RequestBody,
        @Part("Tanggal") Tanggal: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<SurveyPOSTResponse>


    @GET("/survey")
    fun getSurvey(@Header("Authorization") token: String): Call<SurveyGETResponse>


    @GET("/filtermandor")
    fun getMandor(@Header("Authorization") token: String): Call<List<MandorResponseItem>>

    @GET("/register")
    fun getAccount(
        @Header("Authorization") token: String
    ): Call<AccountGETResponse>


    @PUT("/register")
    fun updateAccount(@Header("Authorization") token: String, @Body userRequest: User): Call<AccountPUTResponse>

    @DELETE("/register")
    fun deleteAccount(
        @Header("Authorization") token: String
    ): Call<AccountGETResponse>


}

