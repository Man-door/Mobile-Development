package com.dicoding.mandoor.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.Booking
import com.dicoding.mandoor.api.ApiConfig
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

    // Fetch news from API
    fun fetchNews() {
        viewModelScope.launch(Dispatchers.IO) {
            ApiConfig.instance.getNews().enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles?.filterNotNull() ?: emptyList()
                        newsList.postValue(articles)
                    } else {
                        // Handle error (can be shown as toast in fragment later)
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    // Handle error (can be shown as toast in fragment later)
                }
            })
        }
    }

    // Populate booking list (optional, can be dynamic)
    fun fetchBookings() {
        // You can replace this with dynamic data fetching if needed
        val sampleBookings = listOf(
            Booking(R.drawable.pak_vinsen, "Asep", "Renovasi", "Rp. 200,000", "Ongoing", "14 Nov 2024")
        )
        bookingList.postValue(sampleBookings)
    }
}