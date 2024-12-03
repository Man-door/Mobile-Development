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

    private val _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean> get() = _registerSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun registerUser(fullName: String, username: String, email: String, password: String) {
        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Please fill all fields"
            return
        }

        _loading.value = true
        val request = RegUserRequest(fullName, username, email, password)

        ApiConfig.instance.registerUser(request).enqueue(object : Callback<RegUserResponse> {
            override fun onResponse(call: Call<RegUserResponse>, response: Response<RegUserResponse>) {
                _loading.value = false
                if (response.isSuccessful) {
                    _registerSuccess.value = true
                } else {
                    _errorMessage.value = "Registration Failed: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<RegUserResponse>, t: Throwable) {
                _loading.value = false
                _errorMessage.value = "Network Error: ${t.message}"
            }
        })
    }

    fun registerMandor(fullName: String, username: String, email: String, password: String) {
        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Please fill all fields"
            return
        }

        _loading.value = true
        val request = RegUserRequest(fullName, username, email, password)

        ApiConfig.instance.registerUser(request).enqueue(object : Callback<RegUserResponse> {
            override fun onResponse(call: Call<RegUserResponse>, response: Response<RegUserResponse>) {
                _loading.value = false
                if (response.isSuccessful) {
                    _registerSuccess.value = true
                } else {
                    _errorMessage.value = "Registration Failed: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<RegUserResponse>, t: Throwable) {
                _loading.value = false
                _errorMessage.value = "Network Error: ${t.message}"
            }
        })
    }
}