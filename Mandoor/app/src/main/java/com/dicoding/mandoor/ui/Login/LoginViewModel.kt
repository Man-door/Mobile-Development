package com.dicoding.mandoor.ui.Login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.api.LogUserRequest
import com.dicoding.mandoor.response.LogUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Please fill all fields"
            return
        }

        _loading.value = true

        val request = LogUserRequest(email, password)
        ApiConfig.instance.loginUser(request).enqueue(object : Callback<LogUserResponse> {
            override fun onResponse(call: Call<LogUserResponse>, response: Response<LogUserResponse>) {
                _loading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val sharedPreferences = getApplication<Application>().getSharedPreferences("UserSession", Application.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("user_token", responseBody?.token)
                    editor.apply()

                    _loginSuccess.value = true
                } else {
                    _errorMessage.value = "Login Failed: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<LogUserResponse>, t: Throwable) {
                _loading.value = false
                _errorMessage.value = "Network Error: ${t.message}"
            }
        })
    }
}
