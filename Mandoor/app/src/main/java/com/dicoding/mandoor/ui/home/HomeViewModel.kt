package com.dicoding.mandoor.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.Booking
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.response.AccountGETResponse
import com.dicoding.mandoor.response.ArticlesItem
import com.dicoding.mandoor.response.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val newsList = MutableLiveData<List<ArticlesItem>>()
    val bookingList = MutableLiveData<List<Booking>>()
    val accountData = MutableLiveData<AccountGETResponse?>()

    fun fetchNews() {
        viewModelScope.launch(Dispatchers.IO) {
            ApiConfig.mainInstance.getNews().enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles?.filterNotNull() ?: emptyList()
                        newsList.postValue(articles)
                    } else {
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                }
            })
        }
    }

    fun fetchBookings() {
        val sampleBookings = listOf(
            Booking(R.drawable.image_mandor, "Asep", "Renovasi", "Rp. 200,000", "Ongoing")
        )
        bookingList.postValue(sampleBookings)
    }

    fun fetchAccountData(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val bearerToken = "Bearer $token" // Format Bearer
            ApiConfig.mainInstance.getAccount(bearerToken).enqueue(object : Callback<AccountGETResponse> {
                override fun onResponse(call: Call<AccountGETResponse>, response: Response<AccountGETResponse>) {
                    if (response.isSuccessful) {
                        accountData.postValue(response.body())
                    } else {
                        Log.e("HomeViewModel", "Error: ${response.code()}")
                        accountData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<AccountGETResponse>, t: Throwable) {
                    Log.e("HomeViewModel", "Failure: ${t.message}")
                    accountData.postValue(null)
                }
            })
        }
    }

}