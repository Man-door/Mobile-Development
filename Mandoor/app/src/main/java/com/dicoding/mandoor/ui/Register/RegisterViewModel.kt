package com.dicoding.mandoor.ui.Register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.api.RegUserRequest
import com.dicoding.mandoor.response.RegUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _registrationResult = MutableLiveData<RegUserResponse>()
    val registrationResult: LiveData<RegUserResponse> get() = _registrationResult

    fun registerUser(fullName: String, username: String, email: String, password: String) {
        _loading.value = true
        val apiService = ApiConfig.mainInstance

        val registerRequest = RegUserRequest(fullName, username, email, password)

        apiService.registerUser(registerRequest).enqueue(object : Callback<RegUserResponse> {
            override fun onResponse(call: Call<RegUserResponse>, response: Response<RegUserResponse>) {
                _loading.value = false
                if (response.isSuccessful) {
                    _registrationResult.value = response.body()
                } else {
                    _registrationResult.value = null
                }
            }

            override fun onFailure(call: Call<RegUserResponse>, t: Throwable) {
                _loading.value = false
                _registrationResult.value = null
            }
        })
    }
}
